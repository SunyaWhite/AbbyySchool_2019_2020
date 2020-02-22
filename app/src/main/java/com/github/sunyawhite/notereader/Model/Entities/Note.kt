import java.util.*

open class Note (var Id : Long = 0,
                 var Date : Date = Date(),
                 var Text : String = "",
                 var DrawableRes : Int = 0) {

    override fun toString(): String {
        return "Note Id : ${Id} | Note Date : ${Date.toString()}"
    }

}