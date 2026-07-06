//> using file "project.scala"
//> using dep com.github.kwhat:jnativehook:2.2.2

/**
 * @deprecated This example is deprecated for Wayland on Linux. It may not work as expected on Wayland sessions. Consider using alternative methods for global key event listening on Wayland.
 * 
 * emerging openjdk project Wakefield
 * https://www.youtube.com/watch?v=5gfZDx6IOD4
 * This example demonstrates how to use the JNativeHook library to listen for global key events in a ZIO application.
*/

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.{NativeKeyListener,NativeKeyEvent};

import zio._
import zio.Console._

object ZIOKeyListener extends ZIOAppDefault :

  // Register the global native hook
  def registerGlobalHook: ZIO[Any, Throwable, Unit] = 
    ZIO.attempt(GlobalScreen.registerNativeHook())

  // Unregister the global native hook
  def unregisterGlobalHook: ZIO[Any, Nothing, Unit] = 
    ZIO.attempt {
      println("Unregistering native hook...")
      GlobalScreen.unregisterNativeHook()
    }.orDie


  // Create and add the listener (acquire)
  lazy val ziokeylistener =    // Create and add the listener (acquire)
      ZIO.attempt {
        val listener = new NativeKeyListener {
          override def nativeKeyPressed(e: NativeKeyEvent): Unit = {
            val keyText = NativeKeyEvent.getKeyText(e.getKeyCode)
            Unsafe.unsafe { implicit unsafe =>
              runtime.unsafe.runToFuture(
                printLine(s"Key PRESSED: $keyText, ${e.getKeyCode}, ")
              )
            }
          }

          override def nativeKeyReleased(e: NativeKeyEvent): Unit = {
            val keyText = NativeKeyEvent.getKeyText(e.getKeyCode)
            Unsafe.unsafe { implicit unsafe =>
              runtime.unsafe.runToFuture(
                printLine(s"Key RELEASED: $keyText")
              )
            }
          }

          override def nativeKeyTyped(e: NativeKeyEvent): Unit = ()
        }
        GlobalScreen.addNativeKeyListener(listener)
        listener
      }


  // Create and add a key listener
  def createKeyListener(runtime: Runtime[Any]): ZIO[Scope, Throwable, NativeKeyListener] = {
    //acquireRelease is used as an ARM (automatic resource management) which governs accessing and cleaning up resources
    ZIO.acquireRelease(ziokeylistener) { listener =>
        // Remove the listener (release)
        ZIO.attempt {
          GlobalScreen.removeNativeKeyListener(listener)
        }.orDie
    }
  }

  // Main ZIO program
  override def run: ZIO[Any, Throwable, Unit] = {
    ZIO.scoped {
      for {
        _       <- ZIO.acquireRelease(registerGlobalHook)(_ => unregisterGlobalHook)
        _       <- printLine("Starting keyboard listener (Press Enter to exit)...")
        runtime <- ZIO.runtime[Any]
        _       <- createKeyListener(runtime).fork
        _       <- printLine("Listeners started. Press any key to see output...")
        _       <- printLine("Press Enter in this console to exit...")
        _       <- Console.readLine
        _       <- printLine("Goodbye!")
      } yield ()
    }.provideLayer(Scope.default)
  }


ZIOKeyListener.main(Array.empty)