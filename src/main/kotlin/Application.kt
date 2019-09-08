import javafx.event.EventHandler
import javafx.scene.control.SplitPane
import javafx.stage.WindowEvent
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
            menu("View"){
                item("Answers").setOnAction {
                    AnswersView().openWindow()
                }
                menu("Zoom"){
                    item("In").setOnAction {
                        questionView.resize(1)
                    }
                    item("Out").setOnAction {
                        questionView.resize(-1)
                    }
                    item("Reset").setOnAction {
                        questionView.resize(0)
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
        currentStage?.addEventHandler(WindowEvent.WINDOW_SHOWING, EventHandler {
            sp.setDividerPositions(.80)
        })

        runLater(1.seconds) {
            sp.setDividerPositions(.75)
        }
    }

    fun loadQuestion(){
        questionView.chooseQuestion()
        questionView.loadAnswers()
    }

    fun loadTeams(){
        val chooser = JFileChooser()
        chooser.dialogTitle = "Open Team Names"
        chooser.fileFilter = FileNameExtensionFilter("TXT files", "txt")
        chooser.currentDirectory = File("./src/main/resources")
        if(chooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
            val teams = chooser.selectedFile.readText().lines()
            io.teams.populateTeams(teams)
            io.scores.populateScores()
        }
    }
}