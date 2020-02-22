import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class NoteRealm (
    @PrimaryKey
    var Id : Long = 0,
    var Date : Date = Date(),
    var Text : String = "",
    var DrawableRes : Int = 0) : RealmModel {

    override fun toString(): String {
        return "Note Id : ${Id} | Note Date : ${Date.toString()}"
    }

}