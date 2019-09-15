import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.image.Image
import javafx.scene.text.Font
import tornadofx.*

class SelectTeamsView : View("My View") {
    val applicationView: ApplicationView by inject()

    override val root = vbox{
        alignment = Pos.CENTER
        paddingAll = 16

        imageview(Image("group-512.png")){
            fitWidth = 256.0
            fitHeight = 256.0
        }
        label("Select Teams"){
            font = Font(20.0)
            padding = Insets(0.0, 0.0, 16.0, 0.0)
        }
    }

    val groups = listview<String>{
        selectionModel.selectionMode = SelectionMode.SINGLE
        cellFormat {
            graphic = cache{
                label(it.capitalize()){
                    font = Font(18.0)
                }
            }
        }
        setOnMouseClicked {
            if (it.clickCount >= 2) {
                setTeamsAndExit()
            }
        }
    }

    init {
        root += groups
        groups.items = DB.getTeamGroups().asObservable()
        groups.selectionModel.select(0)

        root += hbox {
            padding = Insets(16.0, 0.0, 0.0, 0.0)
            alignment = Pos.BOTTOM_RIGHT
            button("OK").setOnAction {
                setTeamsAndExit()
            }
        }
    }

    private fun setTeamsAndExit(){
        val selected = groups.selectedItem!!
        DB.setPropertyValue(DB.Names.TEAM_GROUP, selected)
        applicationView.loadTeams()
        currentStage?.close()
    }
}
