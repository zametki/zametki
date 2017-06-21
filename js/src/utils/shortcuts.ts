import * as $ from "jquery";

function isABootstrapModalOpen(): boolean {
    return $(".modal.show").length > 0;
}

function isInInput(e: JQueryEventObject): boolean {
    let el = $(e.target);
    return (el.is("input") || el.is("textarea"));
}

function bindWorkspacePageKeys() {
    $(document).on("keydown", function (e) {
        if (isABootstrapModalOpen() || isInInput(e)) {
            return;
        }
        if (e.which === 65 || e.which === 97) {
            const $btn = $("#add-zametka-button");
            if ($btn.hasClass("active-create")) {
                return
            }
            $btn.click();
        } else if (e.which === 27) {
            let clicked = e.originalEvent.srcElement.getAttribute("id");
            console.log(clicked);
            if (clicked === "create-zametka-text-area") {
                $("#create-zametka-cancel-button").click();
            }
        }
    });
}

export default {
    bindWorkspacePageKeys: bindWorkspacePageKeys
}