package castillo.ana.misnotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    var notas=ArrayList<Nota>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        leerNotas()
        notasDePrueba()
        var adapter = AdaptadorNotas(notas, this)
        lvNotas.adapter=adapter

        btnAgregarNota.setOnClickListener{
            var intent= Intent(this,AgregarNotaActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun leerNotas(){
        notas.clear()
        var carpeta = File(ubicacion().absolutePath)
        if(carpeta.exists()){
            var archivos=carpeta.listFiles()
            if(archivos!=null){
                for (archivo in archivos){
                    leerArchivo(archivo)
                }
            }
        }
    }

    private fun leerArchivo(archivo: File?) {
        val fis=FileInputStream(archivo)
        val di=DataInputStream(fis)
        val br=BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var contenido=""

        while(strLine!=null){
            contenido+=strLine
            strLine=br.readLine()
        }
        br.close()
        di.close()
        fis.close()
        var titulo=archivo!!.name.substring(0, archivo!!.name.length-4)
        var nota=Nota(titulo, contenido)
        notas.add(nota)
    }

    private fun ubicacion(): File {
        val carpeta = File(getExternalFilesDir(null), "notas")
        if(!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta
    }

    private fun notasDePrueba() {
        notas.add(Nota("Tarea 1","Investigación: Técnicas de arquitecturas empresariales."))
        notas.add(Nota("Tarea 2","Realizar la práctica 07 de aplicaciones móviles."))
        notas.add(Nota("Tarea 3","Investigación:Ejemplos de sistemas distribuidos."))
    }
}
