import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.*

class TeamView : View() {

    val question: QuestionView by inject()
    val io: IOPanel by inject()

    var teams = listOf<Team>()
        private set

    override val root = gridpane{
        hgap = 8.0
    }

    init {
        populateTeams(listOf("Team 1", "Team 2", "Team 3" ,"Team 4"))
    }

    fun populateTeams(newTeams:List<String>){
        root.removeAllRows()
        teams = newTeams.map { Team(it, 0) }

        teams.forEachIndexed {index, team ->
            val text = Text(team.name)
            text.font = Font(16.0)
            val image = ImageView("question-mark.png")
            image.fitWidth = 16.0
            image.fitHeight = 16.0

            root.add(text, 0, index)
            root.add(TextField(), 1, index)
            root.add(image, 2, index)
        }
    }

    fun checkAnswers() = teams.forEachIndexed{index, team ->
        val textFieldIndex = 3 * index + 1
        val node = root.children[textFieldIndex] as TextField
        (root.children[textFieldIndex + 1] as ImageView).image = when(node.text){
            question.answers!![io.outputLine] -> {
                team.score += 1
                Image("checkmark.png")
            }
            else -> Image("x-mark.png")
        }

    }.also {
        question.questionsAnswered!![io.outputLine] = true
        io.navigation.disableCheck()
        io.scores.populateScores()
    }

    fun resetResponses() = root.children.forEach {
        if(it is ImageView){
            it.image = Image("question-mark.png")
        }
        else if(it is TextField){
            it.text = ""
        }
    }
}
