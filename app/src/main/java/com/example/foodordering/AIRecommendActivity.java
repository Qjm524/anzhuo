package com.example.foodordering;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRecommendActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messages;
    private EditText inputText;
    private ImageButton sendBtn;
    private ProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_recommend);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.ai_recommend);
        toolbar.setNavigationOnClickListener(v -> finish());

        messages = new ArrayList<>();
        messages.add(new ChatMessage("您好！我是您的智能点餐助手，请问有什么可以帮您的？", ChatMessage.TYPE_AI));

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(messages);
        chatRecyclerView.setAdapter(chatAdapter);

        inputText = findViewById(R.id.inputText);
        sendBtn = findViewById(R.id.sendBtn);
        loadingProgress = findViewById(R.id.loadingProgress);

        sendBtn.setOnClickListener(v -> sendMessage());

        inputText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                sendMessage();
                return true;
            }
            return false;
        });
    }

    private void sendMessage() {
        String text = inputText.getText().toString().trim();
        if (text.isEmpty()) {
            return;
        }

        messages.add(new ChatMessage(text, ChatMessage.TYPE_USER));
        inputText.setText("");
        chatAdapter.notifyItemInserted(messages.size() - 1);
        chatRecyclerView.scrollToPosition(messages.size() - 1);

        sendBtn.setEnabled(false);
        loadingProgress.setVisibility(View.VISIBLE);

        String fullMessage = buildMessageWithContext(text);
        AIApiClient.sendMessage(fullMessage, new AIApiClient.OnResponseListener() {
            @Override
            public void onSuccess(String response) {
                messages.add(new ChatMessage(response, ChatMessage.TYPE_AI));
                chatAdapter.notifyItemInserted(messages.size() - 1);
                chatRecyclerView.scrollToPosition(messages.size() - 1);
                sendBtn.setEnabled(true);
                loadingProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {
                String fallbackResponse = generateSmartResponse(text);
                messages.add(new ChatMessage(fallbackResponse, ChatMessage.TYPE_AI));
                chatAdapter.notifyItemInserted(messages.size() - 1);
                chatRecyclerView.scrollToPosition(messages.size() - 1);
                sendBtn.setEnabled(true);
                loadingProgress.setVisibility(View.GONE);
            }
        });
    }

    private String buildMessageWithContext(String userMessage) {
        StringBuilder context = new StringBuilder();
        context.append("你是一个智能点餐助手，我们餐厅有以下菜品：\n");
        for (FoodItem food : AppData.getFoodList()) {
            context.append("- ").append(food.getName()).append("：").append(food.getDescription()).append("，价格").append(food.getPrice()).append("元\n");
        }
        context.append("\n用户的问题是：").append(userMessage);
        context.append("\n请用友好、专业的语气回答用户的问题，帮助用户点餐。");
        return context.toString();
    }

    private String generateSmartResponse(String userMessage) {
        String lowerMsg = userMessage.toLowerCase();

        if (containsAny(lowerMsg, "你是谁", "你叫什么", "自我介绍", "介绍一下")) {
            return getRandomResponse(new String[]{
                    "我是您的智能点餐助手，专门为您推荐美味佳肴！",
                    "您好！我是餐厅的智能点餐助手，可以帮您推荐菜品、介绍菜单哦~",
                    "我是智能点餐小助手，随时为您服务，有什么想吃的尽管问我！",
                    "很高兴认识您！我是您的专属点餐顾问，让我来帮您挑选美食吧！"
            });
        }

        if (containsAny(lowerMsg, "推荐", "好吃", "特色", "招牌")) {
            FoodItem randomFood = getRandomFood();
            String[] responses = {
                    "我们店的招牌菜是" + randomFood.getName() + "，" + randomFood.getDescription() + "，非常受欢迎，价格" + randomFood.getPrice() + "元，强烈推荐您尝试！",
                    "给您推荐我们的人气菜品" + randomFood.getName() + "，" + randomFood.getDescription() + "，味道非常棒，只要" + randomFood.getPrice() + "元，很多客人都回头点这道菜呢！",
                    "我个人很喜欢" + randomFood.getName() + "，" + randomFood.getDescription() + "，价格实惠只要" + randomFood.getPrice() + "元，是我们店的必点菜之一！",
                    "客人们最常点的就是" + randomFood.getName() + "，" + randomFood.getDescription() + "，" + randomFood.getPrice() + "元就能吃到这么美味的菜，非常划算！"
            };
            return getRandomResponse(responses);
        }

        if (containsAny(lowerMsg, "不能吃辣", "不吃辣", "微辣", "不要辣", "清淡")) {
            List<String> nonSpicyFoods = getNonSpicyFoods();
            if (!nonSpicyFoods.isEmpty()) {
                StringBuilder response = new StringBuilder("好的，为您推荐不辣的菜品：\n");
                for (String food : nonSpicyFoods) {
                    response.append("- ").append(food).append("\n");
                }
                response.append("这些菜品口味清淡，非常适合不能吃辣的朋友，您想了解哪一道？");
                return response.toString();
            }
            return "抱歉，我们目前没有不辣的菜品，不过您可以尝试微辣口味，辣度适中哦~";
        }

        if (containsAny(lowerMsg, "辣", "麻辣", "香辣")) {
            List<String> spicyFoods = getSpicyFoods();
            if (!spicyFoods.isEmpty()) {
                StringBuilder response = new StringBuilder("喜欢吃辣的话，推荐您试试这些：\n");
                for (String food : spicyFoods) {
                    response.append("- ").append(food).append("\n");
                }
                response.append("这些菜品麻辣鲜香，非常过瘾，您想试试哪一道？");
                return response.toString();
            }
            return "抱歉，我们目前没有特别辣的菜品，您可以试试其他口味哦~";
        }

        if (containsAny(lowerMsg, "菜单", "菜品", "有什么")) {
            StringBuilder menu = new StringBuilder("我们有以下菜品：\n");
            for (FoodItem food : AppData.getFoodList()) {
                menu.append("- ").append(food.getName()).append("：").append(food.getPrice()).append("元\n");
            }
            menu.append("您可以告诉我想吃什么口味，我来帮您推荐！");
            return menu.toString();
        }

        if (containsAny(lowerMsg, "价格", "多少钱", "贵不贵")) {
            return getRandomResponse(new String[]{
                    "我们的菜品价格非常实惠，大部分都在20-45元之间，性价比很高！",
                    "我们的价格很亲民，最便宜的麻婆豆腐只要18元，最贵的清蒸鲈鱼45元，丰俭由人！",
                    "不用担心价格，我们的定价很合理，人均消费大约30元左右，非常划算！",
                    "我们的菜品价格公道，味道又好，花小钱就能吃到大餐，欢迎品尝！"
            });
        }

        if (containsAny(lowerMsg, "你好", "hi", "hello", "您好")) {
            return getRandomResponse(new String[]{
                    "您好！很高兴为您服务，请问有什么可以帮您的？",
                    "你好呀！我是您的点餐小助手，想吃点什么？",
                    "您好！欢迎光临，让我来帮您推荐美食吧！",
                    "嗨！有什么需要帮忙的吗？我可以为您推荐菜品哦~"
            });
        }

        if (containsAny(lowerMsg, "谢谢", "感谢", "多谢")) {
            return getRandomResponse(new String[]{
                    "不客气！希望您用餐愉快，有问题随时问我！",
                    "不用谢！祝您吃得开心，下次再来哦~",
                    "很高兴能帮到您，祝您用餐愉快！",
                    "不客气啦，希望您喜欢我们的菜品！"
            });
        }

        if (containsAny(lowerMsg, "再见", "拜拜", "下次见")) {
            return getRandomResponse(new String[]{
                    "再见！欢迎下次光临，期待再次为您服务！",
                    "拜拜~希望您吃得开心，下次再来！",
                    "再见啦，祝您有美好的一天！",
                    "期待下次见面，记得常来哦！"
            });
        }

        if (containsAny(lowerMsg, "帮助", "怎么用", "功能")) {
            return "我可以帮您做这些事哦：\n" +
                    "- 推荐菜品（说'推荐'或'好吃的'）\n" +
                    "- 介绍菜品（说菜品名字）\n" +
                    "- 找不辣的菜（说'不能吃辣'）\n" +
                    "- 找辣的菜（说'辣'）\n" +
                    "- 看菜单（说'菜单'）\n" +
                    "- 查询价格（说'多少钱'）\n" +
                    "\n您想了解什么？";
        }

        for (FoodItem food : AppData.getFoodList()) {
            if (lowerMsg.contains(food.getName().toLowerCase())) {
                String[] responses = {
                        food.getName() + "是我们的特色菜品，" + food.getDescription() + "，价格" + food.getPrice() + "元，非常值得一试！",
                        "您问的是" + food.getName() + "对吧？这道菜" + food.getDescription() + "，只要" + food.getPrice() + "元，味道很不错哦！",
                        "我们的" + food.getName() + "很受欢迎，" + food.getDescription() + "，" + food.getPrice() + "元一份，您要来一份吗？",
                        food.getName() + "是我们的招牌之一，" + food.getDescription() + "，价格实惠" + food.getPrice() + "元，很多客人都很喜欢！"
                };
                return getRandomResponse(responses);
            }
        }

        return getRandomResponse(new String[]{
                "抱歉，我不太明白您的意思，可以再说一遍吗？",
                "不好意思，我没听懂，请问您可以换个说法吗？",
                "您说的我不太理解，能详细说明一下吗？",
                "抱歉，我还在学习中，请问有什么我可以帮到您的？"
        });
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String getRandomResponse(String[] responses) {
        Random random = new Random();
        return responses[random.nextInt(responses.length)];
    }

    private FoodItem getRandomFood() {
        List<FoodItem> foodList = AppData.getFoodList();
        Random random = new Random();
        return foodList.get(random.nextInt(foodList.size()));
    }

    private List<String> getNonSpicyFoods() {
        List<String> result = new ArrayList<>();
        String[] nonSpicyNames = {"清蒸鲈鱼", "糖醋排骨", "蒜蓉西兰花"};
        for (FoodItem food : AppData.getFoodList()) {
            for (String name : nonSpicyNames) {
                if (food.getName().equals(name)) {
                    result.add(food.getName() + "（" + food.getPrice() + "元）");
                }
            }
        }
        return result;
    }

    private List<String> getSpicyFoods() {
        List<String> result = new ArrayList<>();
        String[] spicyNames = {"宫保鸡丁", "麻婆豆腐", "鱼香肉丝", "回锅肉"};
        for (FoodItem food : AppData.getFoodList()) {
            for (String name : spicyNames) {
                if (food.getName().equals(name)) {
                    result.add(food.getName() + "（" + food.getPrice() + "元）");
                }
            }
        }
        return result;
    }
}