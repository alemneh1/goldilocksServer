package GoldilocksProd.com.Server.services.commercialService;

import GoldilocksProd.com.Server.projects.CommercialProject;

import java.util.List;

public interface CommercialProjectServices {

    public List<CommercialProject> listAllCommercialProject();

    public CommercialProject createCommercialProject(CommercialProject commercialProject);

    public void deleteCommercialProject(CommercialProject commercialProject);

}
