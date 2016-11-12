
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object index_Scope0 {
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

     object index_Scope1 {
import contexts.product.api.Product

class index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[String,List[Product],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*3.2*/(category: String, products: List[Product]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.45*/("""

"""),_display_(/*5.2*/main()/*5.8*/ {_display_(Seq[Any](format.raw/*5.10*/("""

"""),format.raw/*7.1*/("""<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <h1 class="uk-heading-large">"""),_display_(/*9.39*/category),format.raw/*9.47*/("""</h1>
    </div>
</div>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">

        <ul class="uk-subnav uk-subnav-pill" data-uk-switcher=""""),format.raw/*16.64*/("""{"""),format.raw/*16.65*/("""connect:'#switcher-content'"""),format.raw/*16.92*/("""}"""),format.raw/*16.93*/("""">
            <li class="uk-active"><a href="#">All</a></li>
            <li><a href="#">Drip</a></li>
            <li><a href="#">Espresso</a></li>
            <li><a href="#">Grinders</a></li>
        </ul>

        <ul id="switcher-content" class="uk-switcher">
            <li class="uk-active">
                <div class="uk-grid" data-uk-grid-margin>

                    """),_display_(/*27.22*/for(product <- products) yield /*27.46*/ {_display_(Seq[Any](format.raw/*27.48*/("""

                        """),format.raw/*29.25*/("""<div class="uk-width-medium-1-4">
                            <div class="uk-thumbnail">
                                <img width="600" height="400" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjwhLS0gR2VuZXJhdG9yOiBBZG9iZSBJbGx1c3RyYXRvciAxNi4wLjQsIFNWRyBFeHBvcnQgUGx1Zy1JbiAuIFNWRyBWZXJzaW9uOiA2LjAwIEJ1aWxkIDApICAtLT4NCjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+DQo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkViZW5lXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB3aWR0aD0iNjAwcHgiIGhlaWdodD0iNDAwcHgiIHZpZXdCb3g9IjAgMCA2MDAgNDAwIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA2MDAgNDAwIiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxyZWN0IGZpbGw9IiNGNUY1RjUiIHdpZHRoPSI2MDAiIGhlaWdodD0iNDAwIi8+DQo8ZyBvcGFjaXR5PSIwLjciPg0KCTxwYXRoIGZpbGw9IiNEOEQ4RDgiIGQ9Ik0yMjguMTg0LDE0My41djExM2gxNDMuNjMydi0xMTNIMjI4LjE4NHogTTM2MC4yNDQsMjQ0LjI0N0gyNDAuNDM3di04OC40OTRoMTE5LjgwOEwzNjAuMjQ0LDI0NC4yNDcNCgkJTDM2MC4yNDQsMjQ0LjI0N3oiLz4NCgk8cG9seWdvbiBmaWxsPSIjRDhEOEQ4IiBwb2ludHM9IjI0Ni44ODEsMjM0LjcxNyAyNzEuNTcyLDIwOC43NjQgMjgwLjgyNCwyMTIuNzY4IDMxMC4wMTYsMTgxLjY4OCAzMjEuNTA1LDE5NS40MzQgDQoJCTMyNi42ODksMTkyLjMwMyAzNTQuNzQ2LDIzNC43MTcgCSIvPg0KCTxjaXJjbGUgZmlsbD0iI0Q4RDhEOCIgY3g9IjI3NS40MDUiIGN5PSIxNzguMjU3IiByPSIxMC43ODciLz4NCjwvZz4NCjwvc3ZnPg0K" alt="">
                                <h3 style="margin-bottom:10px; padding-bottom:0px;">
                                    <a href="">"""),_display_(/*33.49*/product/*33.56*/.getName()),format.raw/*33.66*/("""</a>
                                </h3>
                                <p style="margin-top:0px; margin-bottom:0px;">
                                    Drip
                                </p>
                                <h2 style="margin-bottom:0px; margin-top:10px;">
                                    <span class="dollars">$64</span><span class="cents">.99</span>
                                </h2>
                                <p style="margin-bottom:0px; margin-top:10px;">
                                    Inventory: 1
                                </p>
                            </div>
                        </div>

                    """)))}),format.raw/*47.22*/("""

                """),format.raw/*49.17*/("""</div>
            </li>
        </ul>
    </div>
</div>

""")))}),format.raw/*55.2*/("""
"""))
      }
    }
  }

  def render(category:String,products:List[Product]): play.twirl.api.HtmlFormat.Appendable = apply(category,products)

  def f:((String,List[Product]) => play.twirl.api.HtmlFormat.Appendable) = (category,products) => apply(category,products)

  def ref: this.type = this

}


}
}

/**/
object index extends index_Scope0.index_Scope1.index
              /*
                  -- GENERATED --
                  DATE: Sat Nov 12 12:03:04 EST 2016
                  SOURCE: /Users/kevin/Development/reactive-commerce/app/views/index.scala.html
                  HASH: 2a9c66fbf71c6b2e8369791fa7423e4c64a20e93
                  MATRIX: 823->39|961->82|989->85|1002->91|1041->93|1069->95|1206->206|1234->214|1424->376|1453->377|1508->404|1537->405|1945->786|1985->810|2025->812|2079->838|3663->2395|3679->2402|3710->2412|4413->3084|4459->3102|4548->3161
                  LINES: 30->3|35->3|37->5|37->5|37->5|39->7|41->9|41->9|48->16|48->16|48->16|48->16|59->27|59->27|59->27|61->29|65->33|65->33|65->33|79->47|81->49|87->55
                  -- GENERATED --
              */
          