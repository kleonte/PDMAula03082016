package br.edu.ifpb.aulapremiumbancodedados;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog.Builder;

public class main extends Activity {

    // Primeira Parte

    EditText editNome, editContato, editTipo;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Segunda Parte

        editNome = (EditText) findViewById(R.id.editNome);
        editContato = (EditText) findViewById(R.id.editContato);
        editTipo = (EditText) findViewById(R.id.editTipo);

        // Terceira Parte

        db = openOrCreateDatabase("ContatosDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS contatos (Nome VARCHAR, Contato VARCHAR, Tipo VARCHAR);");
    }


    // Botão Adicionar

    public void btnadd (View View) {
        if (editNome.getText().toString().trim().length() == 0 || editContato.getText().toString().trim().length() == 0 || editTipo.getText().toString().trim().length() == 0)
        {
            showMessage ("Erro", "Preencha Corretamente os Valores");
            return;
        }
        db.execSQL("INSERT INTO contatos VALUES('"+editNome.getText()+"','"+editContato.getText()+"','"+editTipo.getText()+"');");
        showMessage ("Ok", "Dados Gravados");
        clearText();
    }

    // Botão Deletar

    public void btndel (View View) {
        if (editNome.getText().toString().trim().length() == 0)
        {
            showMessage ("Erro", "Entre com o Nome");
            return;
        }
        Cursor c = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
        if (c.moveToFirst())
        {
            db.execSQL("DELETE FROM contatos WHERE Nome='"+editNome.getText()+"'");
            showMessage ("Sucesso", "Dados Deletados");
        }
        else
        {
            showMessage ("Erro", "Inválido");
        }
        clearText();
    }

    public void btnsalvaredit (View View) {
        if (editNome.getText().toString().trim().length() == 0)
        {
            showMessage ("Erro", "Favor entrar com o Nome");
            return;
        }
        Cursor c = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
        if (c.moveToFirst())
        {
            db.execSQL("UPDATE contatos SET Nome='" + editNome.getText() + "', Contato='" + editContato.getText() + "', Tipo='" + editTipo.getText() + "' WHERE Nome='"+editNome.getText()+"'");
            showMessage ("Sucesso", "Dados Editados");
        }
        else
        {
            showMessage ("Erro", "Faça uma Busca Primeiro");
            clearText();
        }
    }

    // Botão Buscar Contato

    public void btnbuscarcontato (View View) {
        if (editNome.getText().toString().trim().length() == 0)
        {
            showMessage ("Erro", "Favor entrar com o Nome");
            return;
        }
        Cursor c = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+editNome.getText()+"'", null);
        if (c.moveToFirst())
        {
            editNome.setText(c.getString(0));
            editContato.setText(c.getString(1));
            editTipo.setText(c.getString(2));
        }
        else
        {
            showMessage ("Erro", "Nome inválido");
            clearText();
        }
    }

    // Botão Ver Lista de Contatos

    public void btnlistacontatos (View View) {
        Cursor c = db.rawQuery("SELECT * FROM contatos", null);
        if (c.getCount() == 0)
        {
            showMessage ("Erro", "Nada Encontrado");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext())
        {
            buffer.append("Nome: " + c.getString(0)+"\n");
        }
        showMessage ("Detalhes dos Contatos", buffer.toString());
    }

    // ShowMessage

    public void showMessage (String title, String message)
    {
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    // ClearText

    public void clearText()
    {
        editNome.setText("");
        editContato.setText("");
        editTipo.setText("");
    }

    // Botão Limpar

    public void limparcampos (View View)
    {
        editNome.setText("");
        editContato.setText("");
        editTipo.setText("");
    }
}
