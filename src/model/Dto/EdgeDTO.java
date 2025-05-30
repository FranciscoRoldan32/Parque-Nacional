package model.Dto;

public class EdgeDTO {
	private final String origen;
    private final String destino;
    private final int peso;
    
    public EdgeDTO(String origen, String destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		  return origen + " -> " + destino + " (" + peso + ")";
	}
	

}
