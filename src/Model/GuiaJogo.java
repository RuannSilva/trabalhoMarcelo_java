package Model;

public class GuiaJogo {


    private int idVideo, tempoDuracaoMinuto;
    private String objetivo, nomeJogo;

    public GuiaJogo(int idVideo, int tempoDuracaoMinuto, String objetivo, String nomeJogo) {
        this.idVideo = idVideo;
        this.tempoDuracaoMinuto = tempoDuracaoMinuto;
        this.objetivo = objetivo;
        this.nomeJogo = nomeJogo;
    }

    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }

    public int getTempoDuracaoMinuto() {
        return tempoDuracaoMinuto;
    }

    public void setTempoDuracaoMinuto(int tempoDuracaoMinuto) {
        this.tempoDuracaoMinuto = tempoDuracaoMinuto;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }
}
