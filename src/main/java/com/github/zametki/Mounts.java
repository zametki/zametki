package com.github.zametki;

import com.github.zametki.ajax.CreateGroupAjaxCall;
import com.github.zametki.ajax.CreateNoteAjaxCall;
import com.github.zametki.ajax.DeleteGroupAjaxCall;
import com.github.zametki.ajax.DeleteNoteAjaxCall;
import com.github.zametki.ajax.GetNotesListAjaxCall;
import com.github.zametki.ajax.MoveGroupAjaxCall;
import com.github.zametki.ajax.MoveNoteAjaxCall;
import com.github.zametki.ajax.RenameGroupAjaxCall;
import com.github.zametki.ajax.UpdateNoteAjaxCall;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.InternalErrorPage;
import com.github.zametki.component.LogoutPage;
import com.github.zametki.component.PageNotFoundPage;
import com.github.zametki.component.WorkspacePage;
import com.github.zametki.component.help.AboutPage;
import com.github.zametki.component.signin.ForgotPasswordPage;
import com.github.zametki.component.signin.ResetPasswordPage;
import com.github.zametki.component.user.UserProfileSettingsPage;
import org.apache.wicket.request.component.IRequestablePage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class Mounts {
    static void mountAll(ZApplication app) {
        mountPages(app,
                // Base pages
                InternalErrorPage.class,
                PageNotFoundPage.class,
                LogoutPage.class,

                // Reset passwords
                ForgotPasswordPage.class,
                ResetPasswordPage.class,

                // User pages
                WorkspacePage.class,
                UserProfileSettingsPage.class,

                // Public resources
                AboutPage.class
        );

        mountResources(app,
                CreateGroupAjaxCall.class,
                DeleteGroupAjaxCall.class,
                GetNotesListAjaxCall.class,
                MoveGroupAjaxCall.class,
                RenameGroupAjaxCall.class,
                MoveNoteAjaxCall.class,
                CreateNoteAjaxCall.class,
                UpdateNoteAjaxCall.class,
                DeleteNoteAjaxCall.class
        );
    }

    @Nullable
    public static String mountPath(@NotNull Class<? extends IRequestablePage> pageClass) {
        MountPath a = pageClass.getAnnotation(MountPath.class);
        return a == null ? null : mountPath(a.value());
    }

    @NotNull
    public static String mountPath(@NotNull String mountPathWithParams) {
        int first$Idx = mountPathWithParams.indexOf('$');
        if (first$Idx == -1) {
            return mountPathWithParams;
        }
        String res = mountPathWithParams.substring(0, first$Idx);
        int lastSlashIdx = res.lastIndexOf('/');
        return lastSlashIdx == -1 ? mountPathWithParams : res.substring(0, lastSlashIdx);
    }

    private static void mountPages(@NotNull ZApplication app, Class... classes) {
        //noinspection unchecked
        Arrays.stream(classes).forEach(app::mountPage);
    }

    private static void mountResources(@NotNull ZApplication app, Class... classes) {
        //noinspection unchecked
        Arrays.stream(classes).forEach(app::mountResource);
    }

    @NotNull
    public static String urlFor(Class<? extends IRequestablePage> pageClass) {
        return Context.getBaseUrl() + mountPath(pageClass);
    }

    public static boolean isMounted(@NotNull Class<? extends IRequestablePage> pageClass) {
        return ZApplication.get().isMounted(pageClass);
    }

}
