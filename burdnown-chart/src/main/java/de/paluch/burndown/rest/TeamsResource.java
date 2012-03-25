package de.paluch.burndown.rest;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jfree.chart.JFreeChart;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.SprintDaysGenerator;
import de.paluch.burndown.chart.ChartDataFactory;
import de.paluch.burndown.chart.ChartFactory;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.Team;
import de.paluch.burndown.model.Teams;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 21.03.2012
 *<br>
 *<br>
 */
@Path("/teams")
public class TeamsResource
{

	@GET
	@Path("/{teamId}/sprints/{sprintId}.png")
	@Produces("image/png")
	public Response createChart(@PathParam("teamId")
	String teamId, @PathParam("sprintId")
	String sprintId, @QueryParam("width")
	@DefaultValue("1024")
	int width, @QueryParam("height")
	@DefaultValue("786")
	int height, @QueryParam("multiplier")
	@DefaultValue("10")
	double multiplier) throws FontFormatException, IOException
	{

		double normalizedMultiplier = multiplier / 10;
		byte[] result = new byte[0];
		Sprint sprint = new DataAccess().getSprint(teamId, sprintId);
		Team team = getTeam(teamId);

		if (sprint == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}

		SprintDaysGenerator gen = new SprintDaysGenerator();
		ChartDataFactory dataFactory = new ChartDataFactory();
		ChartFactory chartFactory = new ChartFactory();

		dataFactory.createData(team.getTeamSize(), sprint,
				gen.generateSprintDays(sprint.getDays() + 1, sprint.getStartDate()));

		JFreeChart chart = chartFactory.createChart(dataFactory.getChartData());

		BufferedImage image = chart.createBufferedImage(width, height, (width / normalizedMultiplier),
				(height / normalizedMultiplier),
				null);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		baos.close();
		result = baos.toByteArray();
		return Response.ok(result).build();
	}

	@GET
	@Path("{teamId}/sprints/{sprintId}")
	@Produces(MediaType.TEXT_XML)
	public Response getSprint(@PathParam("teamId")
	String teamId, @PathParam("sprintId")
	String sprintId)
	{

		Sprint sprint = new DataAccess().getSprint(teamId, sprintId);
		if (sprint == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(sprint).build();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public Teams listTeams()
	{

		return new DataAccess().getTeams();
	}

	@PUT
	@Path("{teamId}/sprints/{sprintId}")
	@Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_XML})
	@Produces(MediaType.TEXT_XML)
	public Response storeSprint(@PathParam("teamId")
	String teamId, @PathParam("sprintId")
	String sprintId, Sprint sprint)
	{

		sprint.setId(sprintId);

		new DataAccess().storeSprint(teamId, sprint);
		return Response
				.created(UriBuilder.fromResource(getClass()).path(teamId).path("sprints").path(sprintId).build())
				.build();
	}

	@PUT
	@Path("{teamId}")
	@Produces(MediaType.TEXT_XML)
	@Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_XML})
	public void storeTeam(@PathParam("teamId")
	String teamId,
			Teams teams)
	{

		for (Team team : teams.getTeams())
		{
			new DataAccess().saveOrUpdateTeam(team);
		}
	}

	/**
	 * @param teamId
	 * @return
	 */
	private Team getTeam(String teamId)
	{

		Teams teams = listTeams();
		for (Team team : teams.getTeams())
		{
			if (team.getId().equals(teamId))
			{
				return team;
			}
		}
		return null;
	}

}
