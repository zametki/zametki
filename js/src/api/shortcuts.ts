import * as $ from "jquery";

function isABootstrapModalOpen() {
    return $(".modal.show").length > 0;
}

function bindWorkspacePageKeys() {
    $(document).on("keyup", function (e) {
        if (isABootstrapModalOpen()) {
            return;
        }
        if (e.which === 65 || e.which === 97) {
            const $btn = $("#add-zametka-button");
            if ($btn.hasClass("active-create")) {
                return
            }
            $btn.click();
        } else if (e.which === 27) {
        }
    });
}

export default {
    bindWorkspacePageKeys: bindWorkspacePageKeys
}