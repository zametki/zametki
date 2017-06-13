import site from "./api/site-def";
import "./api/parsley-translations";
import ReactUtils from "./api/react-utils";
import SiteUtils from "./api/site-utils";
import Shortcuts from "./api/shortcuts";
import Ajax from "./api/ajax";

site.ReactUtils = ReactUtils;
site.Utils = SiteUtils;
site.Shortcuts = Shortcuts;
site.Ajax = Ajax;

(<any>window).$site = site;

