package com.assetco.hotspots.optimization;

import com.assetco.search.results.Asset;
import com.assetco.search.results.AssetVendor;
import com.assetco.search.results.AssetVendorRelationshipLevel;
import com.assetco.search.results.HotspotKey;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BugsTest {

    @Test
    public void precedingPartnerWithLongTrailingAssetsDoesNotWin() throws Exception {
        AssetVendorRelationshipLevel relationshipLevel = AssetVendorRelationshipLevel.Basic;
        AssetVendor partnerVendor = makeVendor(relationshipLevel);
        AssetVendor otherVendor = makeVendor(relationshipLevel);
        Asset missing =  givenAssetInResultsWithVendor(partnerVendor);
        Asset missing2 =  givenAssetInResultsWithVendor(otherVendor);

        List<Asset> expected = List.of(
                givenAssetInResultsWithVendor(partnerVendor),
                givenAssetInResultsWithVendor(partnerVendor),
                givenAssetInResultsWithVendor(partnerVendor),
                givenAssetInResultsWithVendor(partnerVendor)
        );
        
        whenOptimize();

        thenHotSpotDoesNotHave(HotspotKey.HighValue, missing);
        thenHotSpotHasExactly(HotspotKey.HighValue, expected);
    }

    private void thenHotSpotHasExactly(HotspotKey highValue, List<Asset> expected) {
    }

    private void thenHotSpotDoesNotHave(HotspotKey highValue, Asset... expected) {
    }

    private void whenOptimize() {
    }

    private Asset givenAssetInResultsWithVendor(AssetVendor partnerVendor) {
        return null;
    }

    private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
        return null;
    }
}
