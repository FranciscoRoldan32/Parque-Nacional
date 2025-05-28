package model.Dto;

public class VertexDto {
	private String label;
	private int id;
	/**
	 * @param label
	 * @param id
	 */
	public VertexDto(String label, int id) {
		super();
		this.label = label;
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
