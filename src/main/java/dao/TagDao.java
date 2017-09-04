package dao;


import generated.tables.Receipts;
import generated.tables.records.TagsRecord;
import generated.tables.records.ReceiptsRecord;
import org.hibernate.validator.constraints.SafeHtml;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;

import javax.swing.text.html.HTML;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public String addTag(String tag,int receiptId){
        List<ReceiptsRecord> rerecord = dsl
                .selectFrom(RECEIPTS)
                .where(RECEIPTS.ID.eq(receiptId))
                .fetch();
        if(rerecord.size() == 0){
            return "no receiptId in receipt";
        }

        List<TagsRecord> trid = dsl
                .selectFrom(TAGS)
                .where(TAGS.RECEIPTID.eq(receiptId))
                .fetch();
        if(trid.size() == 0){
            //receiptId does not exist in TagRecord
            //insert receiptId and tag into TagRecord
            dsl.insertInto(TAGS, TAGS.RECEIPTID, TAGS.TAG)
                    .values(receiptId, tag)
                    .returning(TAGS.ID)
                    .fetchOne();
            return "insert success";
        }
        //else{
            //receiptId exist, tag exist or not
            for(TagsRecord tt : trid){
                if(tt.getTag().equals(tag)){
                    dsl.deleteFrom(TAGS).where(TAGS.RECEIPTID.eq(receiptId)).and(TAGS.TAG.eq(tag)).execute();
                    return "delete";
                }
            }
            dsl.insertInto(TAGS, TAGS.RECEIPTID, TAGS.TAG)
                .values(receiptId, tag)
                .returning(TAGS.ID)
                .fetchOne();
       //./ }

        return "???";
    }

    public List<TagsRecord> getAllTags() {
        return dsl.selectFrom(TAGS).fetch();
    }
    public List<ReceiptsRecord> loacteTag(String tag){
        Result res = dsl.select(RECEIPTS.ID).from(RECEIPTS.innerJoin(TAGS).on(RECEIPTS.ID.eq(TAGS.RECEIPTID))).where(TAGS.TAG.eq(tag)).fetch();
        List<ReceiptsRecord> matchRecord = dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.in(res)).fetch();

        return matchRecord;

    }
}
