
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object main_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class main extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/()(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.19*/("""


"""),format.raw/*4.1*/("""<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href=""""),_display_(/*8.35*/routes/*8.41*/.Assets.versioned("stylesheets/uikit.min.css")),format.raw/*8.87*/("""" />
    <script
            src="https://code.jquery.com/jquery-3.1.1.min.js"
            integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
            crossorigin="anonymous"></script>
    <script src=""""),_display_(/*13.19*/routes/*13.25*/.Assets.versioned("javascripts/uikit.min.js")),format.raw/*13.70*/(""""></script>
</head>

<body>

<div class="uk-container uk-container-center uk-margin-top uk-margin-large-bottom">

    <nav class="uk-navbar uk-margin-large-bottom">
        <a class="uk-navbar-brand uk-hidden-small" href="layouts_frontpage.html">Reactive Commerce</a>
        <ul class="uk-navbar-nav uk-hidden-small">
            <li class="uk-active">
                <a href="layouts_frontpage.html">Categories</a>
            </li>
            <li>
                <a href="layouts_portfolio.html">Account</a>
            </li>
        </ul>
        <a href="#offcanvas" class="uk-navbar-toggle uk-visible-small" data-uk-offcanvas></a>
        <div class="uk-navbar-brand uk-navbar-center uk-visible-small">Brand</div>
    </nav>

    """),_display_(/*34.6*/content),format.raw/*34.13*/("""

"""),format.raw/*36.1*/("""</div>

</body>

</html>"""))
      }
    }
  }

  def render(content:Html): play.twirl.api.HtmlFormat.Appendable = apply()(content)

  def f:(() => (Html) => play.twirl.api.HtmlFormat.Appendable) = () => (content) => apply()(content)

  def ref: this.type = this

}


}

/**/
object main extends main_Scope0.main
              /*
                  -- GENERATED --
                  DATE: Sat Nov 12 11:52:01 EST 2016
                  SOURCE: /Users/kevin/Development/reactive-commerce/app/views/main.scala.html
                  HASH: 3f22ef9ec16d908e206693c8d1d4b074f1a8f55a
                  MATRIX: 741->1|853->18|882->21|992->105|1006->111|1072->157|1318->376|1333->382|1399->427|2165->1167|2193->1174|2222->1176
                  LINES: 27->1|32->1|35->4|39->8|39->8|39->8|44->13|44->13|44->13|65->34|65->34|67->36
                  -- GENERATED --
              */
          