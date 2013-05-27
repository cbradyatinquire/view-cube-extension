package view

import org.nlogo.api._
import org.nlogo.app.App
import javax.swing.SwingUtilities
import org.nlogo.window.JOGLLoadingException

/**
 * Created by IntelliJ IDEA.
 * User: cbrady
 * Date: 5/26/13
 * Time: 9:41 PM
 * To change this template use File | Settings | File Templates.
 */
class ViewCubeExtension extends DefaultClassManager {

  override def load(primManager: PrimitiveManager) {
    primManager.addPrimitive("pop", PopWindow)
  }

  object PopWindow extends DefaultCommand {
    def perform(args: Array[Argument], context: Context) {
      invokeLater(  {
        val ws = App.app.workspace
        try {
          ws.glView.open()
          ws.set2DViewEnabled(false)
        } catch {
          case e:JOGLLoadingException =>
            val message = e.getMessage
            org.nlogo.swing.Utils.alert("3D View", message, "" + e.getCause, I18N.guiJ.get("common.buttons.continue"))
            ws.switchTo3DViewAction.setEnabled(false)
          }
        })
    }

  }



  def invokeLater( codeBlock: => Unit ) {
    SwingUtilities.invokeLater( new Runnable {
      override def run() { codeBlock  }
    })
  }
}
