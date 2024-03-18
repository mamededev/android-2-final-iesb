package dev.mamede.android2finaliesb;

public class Car {
    private String id;
    private String ano;
    private String anotacoes;
    private String cor;
    private String marca;
    private String modelo;
    private String nomeDoProprietario;
    private String placa;

    public Car() {
        // Default constructor required for calls to DataSnapshot.getValue(Car.class)
    }

    public Car(String ano, String anotacoes, String cor, String marca, String modelo, String nomeDoProprietario, String placa) {
        this.ano = ano;
        this.anotacoes = anotacoes;
        this.cor = cor;
        this.marca = marca;
        this.modelo = modelo;
        this.nomeDoProprietario = nomeDoProprietario;
        this.placa = placa;
    }

    public String getId() {
      return id;
  }

  public void setId(String id) {
      this.id = id;
  }
  
    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNomeDoProprietario() {
        return nomeDoProprietario;
    }

    public void setNomeDoProprietario(String nomeDoProprietario) {
        this.nomeDoProprietario = nomeDoProprietario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
public String toString() {
    return "Car{" +
        "cor='" + cor + '\'' +
        ", ano=" + ano +
        '}';
}
}