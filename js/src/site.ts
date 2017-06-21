import site from './utils/site-def'
import './utils/parsley-translations'
import ReactUtils from './utils/react-utils'
import SiteUtils from './utils/site-utils'
import Shortcuts from './utils/shortcuts'
import Ajax from './utils/ajax'

site.ReactUtils = ReactUtils
site.Utils = SiteUtils
site.Shortcuts = Shortcuts
site.Ajax = Ajax;

(<any>window).$site = site

