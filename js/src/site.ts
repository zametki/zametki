import site from './utils/SiteDef'
import './i18n/parsley-translations'
import Server2Client from './utils/Server2Client'
import SiteUtils from './utils/SiteUtils'
import Shortcuts from './utils/Shortcuts'
import Ajax from './utils/Client2Server'

site.Server2Client = Server2Client
site.Utils = SiteUtils
site.Shortcuts = Shortcuts
site.Ajax = Ajax;

(<any>window).$site = site

