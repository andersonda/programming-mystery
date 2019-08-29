import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.*

class TeamView : View() {
    // placeholder for prototype, eventually will read in teams from database
    override val root = gridpane{
        hgap = 8.0
    }

    init {
        populateTeams(listOf("Team 1", "Team 2", "Team 3" ,"Team 4"))
    }


    fun populateTeams(teams:List<String>){
        root.removeAllRows()
        teams.forEachIndexed { index, team ->
            val text = Text(team)
            text.font = Font(16.0)
            val image = ImageView("question-mark.png")
            image.fitWidth = 16.0
            image.fitHeight = 16.0

            root.add(text, 0, index)
            root.add(TextField(), 1, index)
            root.add(image, 2, index)
        }
    }
}
