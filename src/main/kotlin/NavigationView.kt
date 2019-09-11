import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.text.Font
import tornadofx.*

class NavigationView : View() {
    val ioPanelView: IOPanelView by inject()
    val teamView: TeamView by inject()

    private val prev = button() {
        graphic = hbox{
            alignment = Pos.CENTER
            imageview(Image("prev.png")){
                fitWidth = 18.0
                fitHeight = 18.0
            }
            label("Prev"){
                font = Font(18.0)
            }
        }
        setOnAction {
            teamView.saveResponses()
            ioPanelView.prevOutputLine()
            teamView.loadResponses()
        }
    }
    private val check = button("Check") {
        useMaxWidth = true
        font = Font(18.0)
        setOnAction {
            teamView.checkAnswers()
        }
    }
    private val next = button() {
        graphic = hbox{
            alignment = Pos.CENTER
            label("Next"){
                font = Font(18.0)
            }
            imageview(Image("next.png")){
                fitWidth = 18.0
                fitHeight = 18.0
            }
        }
        setOnAction {
            teamView.saveResponses()
            ioPanelView.nextOutputLine()
            teamView.loadResponses()
        }
    }

    override val root = hbox {
        alignment = Pos.CENTER
        spacing = 8.0
        paddingAll = 8.0
    }

    init {
        root += prev
        root += check
        root += next
    }

    fun disableCheck(){
        check.isDisable = true
    }

    fun enableCheck(){
        check.isDisable = false
    }
}
