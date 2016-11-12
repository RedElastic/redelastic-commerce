
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/kevin/Development/reactive-commerce/conf/routes
// @DATE:Sat Nov 12 11:52:00 EST 2016

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseProductController ProductController = new controllers.ReverseProductController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseProductController ProductController = new controllers.javascript.ReverseProductController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
  }

}
