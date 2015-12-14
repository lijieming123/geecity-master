package net.bluemap.geecitypoperty.quality.model;

/**
 * 品质检查项类
 * Created by LiuPeng on 2015/9/14.
 */
public class QualityCheckItem {
    //id
    private int id;
    //清洁项目
    private String project;
    //分值明细
    private String scoreDetail;
    //总分
    private int scoreTotal;
    //上午得分
    private int scoreAM;
    //下午得分
    private int scorePM;
    //备注
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getScoreDetail() {
        return scoreDetail;
    }

    public void setScoreDetail(String scoreDetail) {
        this.scoreDetail = scoreDetail;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public int getScoreAM() {
        return scoreAM;
    }

    public void setScoreAM(int scoreAM) {
        this.scoreAM = scoreAM;
    }

    public int getScorePM() {
        return scorePM;
    }

    public void setScorePM(int scorePM) {
        this.scorePM = scorePM;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
