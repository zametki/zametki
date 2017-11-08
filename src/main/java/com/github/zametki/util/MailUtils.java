package com.github.zametki.util;

import com.github.zametki.Constants;
import com.github.zametki.Mounts;
import com.github.zametki.component.user.UserProfileSettingsPage;
import com.github.zametki.model.User;
import org.jetbrains.annotations.NotNull;

public class MailUtils {

    public static void sendWelcomeEmail(@NotNull User user, @NotNull String password) throws Exception {
        MailClient.sendMail(user.getEmail(), Constants.BRAND_NAME + " - регистрационные данные",
                "Поздравляем Вас с регистрацией на " + Constants.BRAND_NAME + "!\n" +
                        "Ваши данные: \n" +
                        "Имя пользователя: " + user.getLogin() + "\n" +
                        "Пароль: " + password + "\n" +
                        "Вы можете редактировать Ваши данные в персональных настройках " + Mounts.urlFor(UserProfileSettingsPage.class) + "\n\n");
    }
}
