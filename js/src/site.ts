import site from "./api/site-def";
import "./api/parsley-translations";
import SiteUtils from "./api/site-utils";
import Shortcuts from "./api/shortcuts";

site.Utils = SiteUtils;
site.Shortcuts = Shortcuts;

(<any>window).$site = site;

