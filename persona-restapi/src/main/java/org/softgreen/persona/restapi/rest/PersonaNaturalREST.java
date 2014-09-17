package org.softgreen.persona.restapi.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.softgreen.persona.entity.PersonaNatural;

@Path("/personas/naturales")
public interface PersonaNaturalREST {

	@GET
	@Path("/tipoDocumentos")
	@Produces({ "application/xml", "application/json" })
	public Response getTipoDocumentoPersonaNatural();

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id);

	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public Response findByTipoNumeroDocumento(@QueryParam("tipoDocumento") String tipoDocumento, @QueryParam("numeroDocumento") String numeroDocumento);

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response listAll(@QueryParam("filterText") String filterText, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit);

	@GET
	@Path("/count")
	@Produces({ "application/xml", "application/json" })
	public Response countAll();

	@PUT
	@Path("/{id}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response update(@PathParam("id")Long id, PersonaNatural personaNatural);

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(PersonaNatural personaNatural);

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id);
/*
	@GET
	@Path("/{id}/firma")
	@Produces("image/png")
	public Response getFirma(@PathParam("id") String id, @QueryParam("flowChunkNumber") int flowChunkNumber, @QueryParam("flowChunkSize") int flowChunkSize, @QueryParam("flowCurrentChunkSize") int flowCurrentChunkSize, @QueryParam("flowFilename") String flowFilename, @QueryParam("flowIdentifier") String flowIdentifier, @QueryParam("flowRelativePath") String flowRelativePath, @QueryParam("flowTotalChunks") int flowTotalChunks, @QueryParam("flowTotalSize") int flowTotalSize);

	@GET
	@Path("/{id}/foto")
	@Produces("image/png")
	public Response getFoto(@PathParam("id") String id, @QueryParam("flowChunkNumber") int flowChunkNumber, @QueryParam("flowChunkSize") int flowChunkSize, @QueryParam("flowCurrentChunkSize") int flowCurrentChunkSize, @QueryParam("flowFilename") String flowFilename, @QueryParam("flowIdentifier") String flowIdentifier, @QueryParam("flowRelativePath") String flowRelativePath, @QueryParam("flowTotalChunks") int flowTotalChunks, @QueryParam("flowTotalSize") int flowTotalSize);

	@POST
	@Path("/{id}/firma")
	@Consumes("multipart/form-data")
	public Response uploadFirma(@PathParam("id") BigInteger id, MultipartFormDataInput input);

	@POST
	@Path("/{id}/foto")
	@Consumes("multipart/form-data")
	public Response uploadFoto(@PathParam("id") BigInteger id, MultipartFormDataInput input);*/

}
