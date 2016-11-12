
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/kevin/Development/reactive-commerce/conf/routes
// @DATE:Sat Nov 12 11:52:00 EST 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
