import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.text.Font
import tornadofx.*

class CreateTeamsView : View("Create Teams") {
    val applicationView: ApplicationView by inject()

    override val root = vbox{
        alignment = Pos.CENTER
        paddingAll = 16

        imageview(Image("group-512.png")){
            fitWidth = 256.0
            fitHeight = 256.0
        }
        label("Create Teams"){
            font = Font(20.0)
            padding = Insets(0.0, 0.0, 16.0, 0.0)
        }
    }

    val groupName = TextField().apply{
        font = Font(18.0)
    }
    val teams = TextArea().apply{
        font = Font(16.0)
        promptText = "Enter team names, separated by commas"
    }

    init {
        root += borderpane(){
            top = label("Group Name"){
                padding = Insets(0.0, 0.0, 16.0, 0.0)
                font = Font(18.0)
            }
            center = groupName
        }
        root += borderpane() {
            padding = Insets(32.0, 0.0, 0.0, 0.0)
            top = label("Team Names"){
                padding = Insets(0.0, 0.0, 16.0, 0.0)
                font = Font(18.0)
            }
            center = teams
        }
        root += hbox {
            padding = Insets(16.0, 0.0, 0.0, 0.0)
            alignment = Pos.BOTTOM_RIGHT
            button("OK").setOnAction {
                val group = groupName.text
                val teams = teams.text.split(",", ", ").map { it.trim() }
                DB.setPropertyValue(DB.Names.TEAM_GROUP, group)
                DB.createTeams(group, teams)
                applicationView.loadTeams()
                currentStage?.close()
            }
        }
    }
}
