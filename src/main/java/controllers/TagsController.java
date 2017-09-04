package controllers;

import api.CreateTagRequest;
import api.ReceiptResponse;
import api.TagResponse;
import dao.TagDao;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagsController {
    final TagDao tagd;
    public TagsController(TagDao tagd) {
        this.tagd = tagd;
    }

    @PUT
    @Path("/{tag}")
    public void toggleTag(@PathParam("tag") String tagName, int receiptId){
        tagd.addTag(tagName,receiptId);
    }


    @GET
    @Path("")
    public List<TagResponse> getAllTags() {
        List<TagsRecord> tagsRecords = tagd.getAllTags();
        return tagsRecords.stream().map(TagResponse::new).collect(toList());
    }


    @GET
    @Path("/{tag}")
    public List<ReceiptResponse> getTags(@PathParam("tag") String tagName){
        List<ReceiptsRecord> receiptsRecord = tagd.loacteTag(tagName);
        return receiptsRecord.stream().map(ReceiptResponse::new).collect(toList());
    }

}
