package AVL;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Cria um html para facilitar a visualição da arvore
public class VisualTree {
    public void saveVisualTree(String title, String dot){
        String html = """
          <!DOCTYPE html>
          <html>
          <head>
            <title>AVL Tree</title>
            <script src="https://cdn.jsdelivr.net/npm/viz.js@2.1.2/viz.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/viz.js@2.1.2/full.render.js"></script>
          </head>
          <body>
            <h1>AVL Tree Visualization</h1>
            <div id="graph" style="text-align:center;"></div>

            <script>
              const dot = `%s`;

              const viz = new Viz();

              viz.renderSVGElement(dot)
                .then(element => {
                  document.getElementById('graph').appendChild(element);
                })
                .catch(error => {
                  console.error("Error rendering graph:", error);
                });
            </script>
          </body>
          </html>
          """.formatted(dot.replace("\"", "\\\"").replace("\n", "\\n"));
       

        try (FileWriter writer = new FileWriter("AVL/Tree/"+title+".html")) {
            writer.write(html);
            System.out.println("Arquivo HTML gerado: avl_tree.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}