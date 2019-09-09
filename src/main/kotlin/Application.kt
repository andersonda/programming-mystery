import javafx.scene.control.SplitPane
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import tornadofx.*
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.system.exitProcess

class Application: App(ApplicationView::class)

class ApplicationView: View("Programming Mystery") {
    private val questionView: QuestionView by inject()
    val io: IOPanel by inject()
    val sp = SplitPane()

    override val root = borderpane {
        top =  menubar {
            menu("File") {
                menu("Load"){
                    item("Code").setOnAction {
                        loadQuestion()
                    }
                    item("Teams").setOnAction {
                        loadTeams()
                    }
                }
                item("Quit").setOnAction {
                    exitProcess(0)
                }
            }
            menu("View") {
                menu("Answers"){
                    item("All Answers").setOnAction {
                        val view = AnswersView()
                        view.loadAllAnswers()
                        view.openWindow()
                    }
                    item("Current Answer").setOnAction {
                        val view = AnswersView()
                        view.loadAnswer(io.outputLine, questionView.answers!![io.outputLine])
                        view.openWindow()
                    }
                }
                menu("Zoom") {
                    item("In") {
                        accelerator = KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_ANY)
                        setOnAction {
                            questionView.resize(1)
                        }
                    }
                    item("Out"){
                        accelerator = KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_ANY)
                        setOnAction {
                            questionView.resize(-1)
                        }
                    }
                    item("Reset") {
                        accelerator = KeyCodeCombination(KeyCode.BACK_SPACE, KeyCombination.CONTROL_ANY)
                        setOnAction {
                            questionView.resize(0)
                        }
                    }
                }
            }
        }
        center = sp
    }

    init {
        loadQuestion()
        sp += questionView.root
        sp += io.root
        currentStage?.isMaximized = true

        // TODO: this is a hacky solution to get the divider to the correct position. should be revisited
        runLater(.5.seconds) {
            sp.setDividerPositions(.80)
        }
    }

    fun loadQuestion(){
        questionView.chooseQuestion()
        questionView.loadAnswers()
        io.teams.loadResponses(emptyList())
        io.navigation.enableCheck()
    }

    fun loadTeams(){
        val teamPath = File("team-path.txt")
        if(!teamPath.exists()){
            teamPath.writeText("")
        }
        val chooser = JFileChooser()
        chooser.dialogTitle = "Open Team Names"
        chooser.fileFilter = FileNameExtensionFilter("TXT files", "txt")
        chooser.currentDirectory = File(teamPath.readText())
        if(chooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
            val teams = chooser.selectedFile.readText().lines()
            io.teams.populateTeams(teams)
            io.scores.populateScores()
            teamPath.writeText(chooser.selectedFile.parent)
        }
    }
}