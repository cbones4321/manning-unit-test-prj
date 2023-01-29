package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.assetco.search.results.AssetVendorRelationshipLevel.*;
import static com.assetco.search.results.HotspotKey.Showcase;
import static org.junit.jupiter.api.Assertions.*;

public class BugsTest {

    private SearchResults searchResults;

    @BeforeEach
    public void setUp() throws Exception {
        searchResults = new SearchResults();
    }

    @Test
    public void precedingPartnerWithLongTrailingAssetsDoesNotWin() throws Exception {
        AssetVendor partnerVendor = makeVendor(Partner);
        Asset missing =  givenAssetInResultsWithVendor(partnerVendor);
        Asset missing2 =  givenAssetInResultsWithVendor(makeVendor(Partner));

        List<Asset> expected = List.of(
                givenAssetInResultsWithVendor(partnerVendor),
                givenAssetInResultsWithVendor(partnerVendor),
                givenAssetInResultsWithVendor(partnerVendor),
                givenAssetInResultsWithVendor(partnerVendor)
        );
        
        whenOptimize();

        thenHotSpotDoesNotHave(Showcase, missing);
        thenHotSpotHasExactly(Showcase, expected);
    }

    private void thenHotSpotHasExactly(HotspotKey highValue, List<Asset> expected) {
        Hotspot hotspot = searchResults.getHotspot(highValue);

        Asset[] assets = hotspot.getMembers().toArray(new Asset[0]);

        assertArrayEquals(assets, expected.toArray(new Asset[0]));
    }

    private void thenHotSpotDoesNotHave(HotspotKey highValue, Asset... expected) {
        Hotspot hotspot = searchResults.getHotspot(highValue);

        List<Asset> members = hotspot.getMembers();

        Arrays.stream(expected).forEach(asset -> {
            assertFalse(members.contains(asset));
        });

    }

    private void whenOptimize() {
        SearchResultHotspotOptimizer searchResultHotspotOptimizer = new SearchResultHotspotOptimizer();
        searchResultHotspotOptimizer.optimize(searchResults);
    }

    private Asset givenAssetInResultsWithVendor(AssetVendor partnerVendor) {
        AssetPurchaseInfo assetPurchaseInfo = new AssetPurchaseInfo(0, 0, new Money(new BigDecimal(30)), new Money(new BigDecimal(60)));
        Asset asset = new Asset(null, null, null, null, assetPurchaseInfo, assetPurchaseInfo, null, partnerVendor);
        searchResults.addFound(asset);
        return asset;
    }

    private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
        return new AssetVendor(null, null, relationshipLevel, 0.0f);
    }
}
