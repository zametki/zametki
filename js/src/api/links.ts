const KnownImageExtensions = {};
KnownImageExtensions["png"] = true;
KnownImageExtensions["jpg"] = true;
KnownImageExtensions["gif"] = true;

const KnownAudioExtensions = {};
KnownAudioExtensions["mp3"] = true;
KnownAudioExtensions["wav"] = true;
KnownAudioExtensions["ogg"] = true;

function playYoutube(el: HTMLElement) {
    // Create an iFrame with autoplay set to true
    let iframeUrl = "https://www.youtube.com/embed/" + el.id + "?autoplay=1&autohide=1";
    if ($(el).data('params')) {
        iframeUrl += '&' + $(this).data('params')
    }

    // The height and width of the iFrame should be the same as parent
    const iframe = $('<iframe/>', {'frameborder': '0', 'src': iframeUrl, 'width': $(el).width(), 'height': $(el).height()});
    iframe.attr("allowfullscreen", "allowfullscreen");

    // Replace the YouTube thumbnail with YouTube HTML5 Player
    $(el).parent().css("paddingTop", 0);
    $(el).replaceWith(iframe);
}

function getYoutubeVideoId(url: string): string {
    const p = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))((\w|-){11})(?:\S+)?$/;
    return (url.match(p)) ? RegExp.$1 : null;
}

function replaceWithYoutubeEmbed(url: string, fallback: string) {
    let videoId = getYoutubeVideoId(url);
    if (!videoId) {
        return fallback;
    }
    const style = "background-image: url(https://img.youtube.com/vi/" + videoId + "/mqdefault.jpg);";
    return `<div class="youtube-aspect-ratio"><div id='${videoId}' class='youtube' style='${style}' onclick='$site.Utils.playYoutube(this);'><div class='play'></div></div></div>`;
}

function getLinkReplacement(link: string): string {
    const lcLink = link.toLocaleLowerCase();
    let url = link;
    if (lcLink.indexOf("http://") == 0) {
        url = link.substr(7);
    } else if (lcLink.indexOf("https://") == 0) {
        url = link.substr(8);
    }
    const lcUrl = url.toLocaleLowerCase();
    const ext = lcUrl.split('.').pop();
    if (ext in KnownImageExtensions) {
        return `<a href='${link}' target='_blank'><img src='${link}' style='max-width: 400px; max-height: 300px;'></a>`
    }
    if (ext in KnownAudioExtensions) {
        return `<audio controls><source src='${link}'></audio>`
    }
    if (getYoutubeVideoId(url) != null) {
        return replaceWithYoutubeEmbed(url, null);
    }
    return null;
}

function processMediaLinks(text: string): string {
    let res = text;
    let startIdx = res.indexOf("<a href=");
    while (startIdx >= 0) {
        let endIdx = res.indexOf("</a>", startIdx);
        if (endIdx < 0) {
            break;
        }
        const hrefStartIdx = startIdx + 9;
        const hrefEndIdx = res.indexOf('"', hrefStartIdx + 1);
        if (hrefEndIdx > 0) {
            const link = res.substring(hrefStartIdx, hrefEndIdx);
            const replacement = getLinkReplacement(link);
            if (replacement != null) {
                res = res.substring(0, startIdx) + replacement + res.substring(endIdx + 4);
                endIdx = startIdx + replacement.length;
            }
        }
        startIdx = res.indexOf("<a href=", endIdx);
    }
    return res;
}

export default {
    processMediaLinks: processMediaLinks,
    playYoutube: playYoutube
}