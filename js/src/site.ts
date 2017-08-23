import site from './utils/SiteDef'
import './i18n/parsley-translations'
import Server2Client from './utils/Server2Client'
import SiteUtils from './utils/SiteUtils'
import Shortcuts from './utils/Shortcuts'

import './style.js'

site.Server2Client = Server2Client
site.Utils = SiteUtils
site.Shortcuts = Shortcuts;

(window as any).$site = site
