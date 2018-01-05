package com.adobe.training.core.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.models.factory.InvalidAdaptableException;
import org.apache.sling.models.factory.ModelFactory;
import com.adobe.training.core.models.SampleSlingModel;

@SlingServlet(paths = "/bin/slingmodel", methods = "GET")
public class SlingModels extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	@Reference
	ModelFactory modelFactory;
	ResourceResolver resourceResolver;

	public void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		logger.info("inside sling model test servlet");
		response.setContentType("text/html");
		try {

			resourceResolver = resourceResolverFactory
					.getServiceResourceResolver(null);
			Resource resource = resourceResolver
					.getResource("/content/aem63/en/sampleSlingModel/jcr:content");
			if (null == resource)
				logger.error("resource missing");
			if (modelFactory.canCreateFromAdaptable(resource,
					SampleSlingModel.class)) {
				logger.error("canCreateFromAdaptable == true");
				String newline = "\n";
				SampleSlingModel ssm = modelFactory.createModel(resource,
						SampleSlingModel.class);
				response.getWriter().write(
						"Output from Sling Model is Description: "
								+ ssm.getDescription()
								+ "<br/> Number of Child nodes: " + ssm.getSize()
								+ "<br/> Title: " + ssm.getTitle());
				logger.error(newline+"Output from Sling Model is Description: "
						+ ssm.getDescription() + newline
						+ " Number of Child nodes: " + ssm.getSize() + newline
						+ " Title: " + ssm.getTitle());
			} else {
				logger.error("canCreateFromAdaptable == false");
			}
		} catch (InvalidAdaptableException ive) {
			logger.error(ive.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

}