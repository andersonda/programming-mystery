import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.*

class ScoresView : View("Scores") {
    val teamView: TeamView by inject()

    override val root = gridpane{
        hgap = 32.0
        vgap = 8.0
        alignment = Pos.CENTER
    }

    init {
        populateScores()
    }

    fun populateScores(){
        root.removeAllRows()
        var index = 0;
        teamView.teams.forEach { team  ->
            val teamText = Text(team.name)
            teamText.font = Font(18.0)
            val scoreText = Text(team.score.toString())
            scoreText.font = Font(18.0)

            root.add(teamText, 0, index)
            root.add(scoreText, 1, index)
            index += 1;
        }
    }
}
