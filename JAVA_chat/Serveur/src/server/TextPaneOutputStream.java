package server;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class TextPaneOutputStream extends OutputStream {

   private final JTextPane textArea;
   private final StringBuilder sb = new StringBuilder();
   private String title;

   public TextPaneOutputStream(final JTextPane textArea) {
      this.textArea = textArea;
   }

   @Override
   public void flush() {
   }

   @Override
   public void close() {
   }

   @Override
   public void write(int b) throws IOException {

      if (b == '\r')
         return;

      if (b == '\n') {
         final String text = sb.toString() + "\n";
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               appendToPane(textArea,text);
            }
         });
         sb.setLength(0);

         return;
      }

      sb.append((char) b);
   }
   
   private void appendToPane(JTextPane tp, String msg){
	    HTMLDocument doc = (HTMLDocument)tp.getDocument();
	    HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
	    try {
	      editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
	      tp.setCaretPosition(doc.getLength());
	    } catch(Exception e){
	      e.printStackTrace();
	    }
	  }
}