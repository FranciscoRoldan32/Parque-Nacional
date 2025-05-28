package model.Dto;

public class EdgeDto {

	VertexDto srcDto;
	VertexDto destDto;
	int weigth;
	
	/**
	 * @param srcDto
	 * @param destDto
	 * @param weigth
	 */
	public EdgeDto(VertexDto srcDto, VertexDto destDto, int weigth) {
		super();
		this.srcDto = srcDto;
		this.destDto = destDto;
		this.weigth = weigth;
	}

	public VertexDto getSrcDto() {
		return srcDto;
	}

	public void setSrcDto(VertexDto srcDto) {
		this.srcDto = srcDto;
	}

	public VertexDto getDestDto() {
		return destDto;
	}

	public void setDestDto(VertexDto destDto) {
		this.destDto = destDto;
	}

	public int getWeigth() {
		return weigth;
	}

	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}
	
}
