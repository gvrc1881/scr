package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the asset_schedule_activity_record database table.
 * 
 */
@Entity
@Table(name = "asset_schedule_activity_record" , uniqueConstraints={@UniqueConstraint(name = "old_pk_asset_schedule_activity_record_uniq", columnNames ={"data_div", "asset_id", "schedule_date", "schedule_code"})})
@NamedQuery(name="AssetScheduleActivityRecord.findAll", query="SELECT a FROM AssetScheduleActivityRecord a")
public class AssetScheduleActivityRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String a1;

	private String a10;

	private String a100;

	private String a101;

	private String a102;

	private String a103;

	private String a104;

	private String a105;

	private String a106;

	private String a107;

	private String a108;

	private String a109;

	private String a11;

	private String a110;

	private String a111;

	private String a112;

	private String a113;

	private String a114;

	private String a115;

	private String a116;

	private String a117;

	private String a118;

	private String a119;

	private String a12;

	private String a120;

	private String a121;

	private String a122;

	private String a123;

	private String a124;

	private String a125;

	private String a126;

	private String a127;

	private String a128;

	private String a129;

	private String a13;

	private String a130;

	private String a14;

	private String a15;

	private String a16;

	private String a17;

	private String a18;

	private String a19;

	private String a2;

	private String a20;

	private String a21;

	private String a22;

	private String a23;

	private String a24;

	private String a25;

	private String a26;

	private String a27;

	private String a28;

	private String a29;

	private String a3;

	private String a30;

	private String a31;

	private String a32;

	private String a33;

	private String a34;

	private String a35;

	private String a36;

	private String a37;

	private String a38;

	private String a39;

	private String a4;

	private String a40;

	private String a41;

	private String a42;

	private String a43;

	private String a44;

	private String a45;

	private String a46;

	private String a47;

	private String a48;

	private String a49;

	private String a5;

	private String a50;

	private String a51;

	private String a52;

	private String a53;

	private String a54;

	private String a55;

	private String a56;

	private String a57;

	private String a58;

	private String a59;

	private String a6;

	private String a60;

	private String a61;

	private String a62;

	private String a63;

	private String a64;

	private String a65;

	private String a66;

	private String a67;

	private String a68;

	private String a69;

	private String a7;

	private String a70;

	private String a71;

	private String a72;

	private String a73;

	private String a74;

	private String a75;

	private String a76;

	private String a77;

	private String a78;

	private String a79;

	private String a8;

	private String a80;

	private String a81;

	private String a82;

	private String a83;

	private String a84;

	private String a85;

	private String a86;

	private String a87;

	private String a88;

	private String a89;

	private String a9;

	private String a90;

	private String a91;

	private String a92;

	private String a93;

	private String a94;

	private String a95;

	private String a96;

	private String a97;

	private String a98;

	private String a99;

	@Column(name="asset_id")
	private String assetId;

	@Column(name="asset_measure_obser_seq_id")
	private String assetMeasureObserSeqId;

	@Column(name="asset_schedule_history_id")
	private String assetScheduleHistoryId;

	@Column(name="asset_type")
	private String assetType;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="details_of_maint")
	private String detailsOfMaint;

	@Column(name="device_created_stamp")
	private Timestamp deviceCreatedStamp;

	@Column(name="device_id")
	private String deviceId;

	@Column(name="device_last_updated_stamp")
	private Timestamp deviceLastUpdatedStamp;

	@Column(name="device_seq_id")
	private String deviceSeqId;

	@Column(name="done_by")
	private String doneBy;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="initial_of_incharge")
	private String initialOfIncharge;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String m1;

	private String m10;

	private String m11;

	private String m12;

	private String m13;

	private String m14;

	private String m15;

	private String m16;

	private String m17;

	private String m18;

	private String m19;

	private String m2;

	private String m20;

	private String m21;

	private String m22;

	private String m23;

	private String m24;

	private String m25;

	private String m26;

	private String m27;

	private String m28;

	private String m29;

	private String m3;

	private String m30;

	private String m31;

	private String m32;

	private String m33;

	private String m34;

	private String m35;

	private String m36;

	private String m37;

	private String m38;

	private String m39;

	private String m4;

	private String m40;

	private String m41;

	private String m42;

	private String m43;

	private String m44;

	private String m45;

	private String m46;

	private String m47;

	private String m48;

	private String m49;

	private String m5;

	private String m50;

	private String m51;

	private String m52;

	private String m53;

	private String m54;

	private String m55;

	private String m56;

	private String m57;

	private String m58;

	private String m59;

	private String m6;

	private String m60;

	private String m61;

	private String m62;

	private String m63;

	private String m64;

	private String m65;

	private String m66;

	private String m67;

	private String m68;

	private String m69;

	private String m7;

	private String m70;

	private String m8;

	private String m9;

	private String make;

	@Column(columnDefinition="TEXT")
	private String mma1;

	@Column(columnDefinition="TEXT")
	private String mma10;

	@Column(columnDefinition="TEXT")
	private String mma2;

	@Column(columnDefinition="TEXT")
	private String mma3;

	@Column(columnDefinition="TEXT")
	private String mma4;

	@Column(columnDefinition="TEXT")
	private String mma5;

	@Column(columnDefinition="TEXT")
	private String mma6;

	@Column(columnDefinition="TEXT")
	private String mma7;

	@Column(columnDefinition="TEXT")
	private String mma8;

	@Column(columnDefinition="TEXT")
	private String mma9;

	private String model;

	private String remarks;

	@Column(name="schedule_actual_date")
	private Timestamp scheduleActualDate;

	@Column(name="schedule_code")
	private String scheduleCode;

	@Column(name="schedule_date")
	private Timestamp scheduleDate;

	private String status;

	public AssetScheduleActivityRecord() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getA1() {
		return this.a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA10() {
		return this.a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA100() {
		return this.a100;
	}

	public void setA100(String a100) {
		this.a100 = a100;
	}

	public String getA101() {
		return this.a101;
	}

	public void setA101(String a101) {
		this.a101 = a101;
	}

	public String getA102() {
		return this.a102;
	}

	public void setA102(String a102) {
		this.a102 = a102;
	}

	public String getA103() {
		return this.a103;
	}

	public void setA103(String a103) {
		this.a103 = a103;
	}

	public String getA104() {
		return this.a104;
	}

	public void setA104(String a104) {
		this.a104 = a104;
	}

	public String getA105() {
		return this.a105;
	}

	public void setA105(String a105) {
		this.a105 = a105;
	}

	public String getA106() {
		return this.a106;
	}

	public void setA106(String a106) {
		this.a106 = a106;
	}

	public String getA107() {
		return this.a107;
	}

	public void setA107(String a107) {
		this.a107 = a107;
	}

	public String getA108() {
		return this.a108;
	}

	public void setA108(String a108) {
		this.a108 = a108;
	}

	public String getA109() {
		return this.a109;
	}

	public void setA109(String a109) {
		this.a109 = a109;
	}

	public String getA11() {
		return this.a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getA110() {
		return this.a110;
	}

	public void setA110(String a110) {
		this.a110 = a110;
	}

	public String getA111() {
		return this.a111;
	}

	public void setA111(String a111) {
		this.a111 = a111;
	}

	public String getA112() {
		return this.a112;
	}

	public void setA112(String a112) {
		this.a112 = a112;
	}

	public String getA113() {
		return this.a113;
	}

	public void setA113(String a113) {
		this.a113 = a113;
	}

	public String getA114() {
		return this.a114;
	}

	public void setA114(String a114) {
		this.a114 = a114;
	}

	public String getA115() {
		return this.a115;
	}

	public void setA115(String a115) {
		this.a115 = a115;
	}

	public String getA116() {
		return this.a116;
	}

	public void setA116(String a116) {
		this.a116 = a116;
	}

	public String getA117() {
		return this.a117;
	}

	public void setA117(String a117) {
		this.a117 = a117;
	}

	public String getA118() {
		return this.a118;
	}

	public void setA118(String a118) {
		this.a118 = a118;
	}

	public String getA119() {
		return this.a119;
	}

	public void setA119(String a119) {
		this.a119 = a119;
	}

	public String getA12() {
		return this.a12;
	}

	public void setA12(String a12) {
		this.a12 = a12;
	}

	public String getA120() {
		return this.a120;
	}

	public void setA120(String a120) {
		this.a120 = a120;
	}

	public String getA121() {
		return this.a121;
	}

	public void setA121(String a121) {
		this.a121 = a121;
	}

	public String getA122() {
		return this.a122;
	}

	public void setA122(String a122) {
		this.a122 = a122;
	}

	public String getA123() {
		return this.a123;
	}

	public void setA123(String a123) {
		this.a123 = a123;
	}

	public String getA124() {
		return this.a124;
	}

	public void setA124(String a124) {
		this.a124 = a124;
	}

	public String getA125() {
		return this.a125;
	}

	public void setA125(String a125) {
		this.a125 = a125;
	}

	public String getA126() {
		return this.a126;
	}

	public void setA126(String a126) {
		this.a126 = a126;
	}

	public String getA127() {
		return this.a127;
	}

	public void setA127(String a127) {
		this.a127 = a127;
	}

	public String getA128() {
		return this.a128;
	}

	public void setA128(String a128) {
		this.a128 = a128;
	}

	public String getA129() {
		return this.a129;
	}

	public void setA129(String a129) {
		this.a129 = a129;
	}

	public String getA13() {
		return this.a13;
	}

	public void setA13(String a13) {
		this.a13 = a13;
	}

	public String getA130() {
		return this.a130;
	}

	public void setA130(String a130) {
		this.a130 = a130;
	}

	public String getA14() {
		return this.a14;
	}

	public void setA14(String a14) {
		this.a14 = a14;
	}

	public String getA15() {
		return this.a15;
	}

	public void setA15(String a15) {
		this.a15 = a15;
	}

	public String getA16() {
		return this.a16;
	}

	public void setA16(String a16) {
		this.a16 = a16;
	}

	public String getA17() {
		return this.a17;
	}

	public void setA17(String a17) {
		this.a17 = a17;
	}

	public String getA18() {
		return this.a18;
	}

	public void setA18(String a18) {
		this.a18 = a18;
	}

	public String getA19() {
		return this.a19;
	}

	public void setA19(String a19) {
		this.a19 = a19;
	}

	public String getA2() {
		return this.a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA20() {
		return this.a20;
	}

	public void setA20(String a20) {
		this.a20 = a20;
	}

	public String getA21() {
		return this.a21;
	}

	public void setA21(String a21) {
		this.a21 = a21;
	}

	public String getA22() {
		return this.a22;
	}

	public void setA22(String a22) {
		this.a22 = a22;
	}

	public String getA23() {
		return this.a23;
	}

	public void setA23(String a23) {
		this.a23 = a23;
	}

	public String getA24() {
		return this.a24;
	}

	public void setA24(String a24) {
		this.a24 = a24;
	}

	public String getA25() {
		return this.a25;
	}

	public void setA25(String a25) {
		this.a25 = a25;
	}

	public String getA26() {
		return this.a26;
	}

	public void setA26(String a26) {
		this.a26 = a26;
	}

	public String getA27() {
		return this.a27;
	}

	public void setA27(String a27) {
		this.a27 = a27;
	}

	public String getA28() {
		return this.a28;
	}

	public void setA28(String a28) {
		this.a28 = a28;
	}

	public String getA29() {
		return this.a29;
	}

	public void setA29(String a29) {
		this.a29 = a29;
	}

	public String getA3() {
		return this.a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA30() {
		return this.a30;
	}

	public void setA30(String a30) {
		this.a30 = a30;
	}

	public String getA31() {
		return this.a31;
	}

	public void setA31(String a31) {
		this.a31 = a31;
	}

	public String getA32() {
		return this.a32;
	}

	public void setA32(String a32) {
		this.a32 = a32;
	}

	public String getA33() {
		return this.a33;
	}

	public void setA33(String a33) {
		this.a33 = a33;
	}

	public String getA34() {
		return this.a34;
	}

	public void setA34(String a34) {
		this.a34 = a34;
	}

	public String getA35() {
		return this.a35;
	}

	public void setA35(String a35) {
		this.a35 = a35;
	}

	public String getA36() {
		return this.a36;
	}

	public void setA36(String a36) {
		this.a36 = a36;
	}

	public String getA37() {
		return this.a37;
	}

	public void setA37(String a37) {
		this.a37 = a37;
	}

	public String getA38() {
		return this.a38;
	}

	public void setA38(String a38) {
		this.a38 = a38;
	}

	public String getA39() {
		return this.a39;
	}

	public void setA39(String a39) {
		this.a39 = a39;
	}

	public String getA4() {
		return this.a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA40() {
		return this.a40;
	}

	public void setA40(String a40) {
		this.a40 = a40;
	}

	public String getA41() {
		return this.a41;
	}

	public void setA41(String a41) {
		this.a41 = a41;
	}

	public String getA42() {
		return this.a42;
	}

	public void setA42(String a42) {
		this.a42 = a42;
	}

	public String getA43() {
		return this.a43;
	}

	public void setA43(String a43) {
		this.a43 = a43;
	}

	public String getA44() {
		return this.a44;
	}

	public void setA44(String a44) {
		this.a44 = a44;
	}

	public String getA45() {
		return this.a45;
	}

	public void setA45(String a45) {
		this.a45 = a45;
	}

	public String getA46() {
		return this.a46;
	}

	public void setA46(String a46) {
		this.a46 = a46;
	}

	public String getA47() {
		return this.a47;
	}

	public void setA47(String a47) {
		this.a47 = a47;
	}

	public String getA48() {
		return this.a48;
	}

	public void setA48(String a48) {
		this.a48 = a48;
	}

	public String getA49() {
		return this.a49;
	}

	public void setA49(String a49) {
		this.a49 = a49;
	}

	public String getA5() {
		return this.a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getA50() {
		return this.a50;
	}

	public void setA50(String a50) {
		this.a50 = a50;
	}

	public String getA51() {
		return this.a51;
	}

	public void setA51(String a51) {
		this.a51 = a51;
	}

	public String getA52() {
		return this.a52;
	}

	public void setA52(String a52) {
		this.a52 = a52;
	}

	public String getA53() {
		return this.a53;
	}

	public void setA53(String a53) {
		this.a53 = a53;
	}

	public String getA54() {
		return this.a54;
	}

	public void setA54(String a54) {
		this.a54 = a54;
	}

	public String getA55() {
		return this.a55;
	}

	public void setA55(String a55) {
		this.a55 = a55;
	}

	public String getA56() {
		return this.a56;
	}

	public void setA56(String a56) {
		this.a56 = a56;
	}

	public String getA57() {
		return this.a57;
	}

	public void setA57(String a57) {
		this.a57 = a57;
	}

	public String getA58() {
		return this.a58;
	}

	public void setA58(String a58) {
		this.a58 = a58;
	}

	public String getA59() {
		return this.a59;
	}

	public void setA59(String a59) {
		this.a59 = a59;
	}

	public String getA6() {
		return this.a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA60() {
		return this.a60;
	}

	public void setA60(String a60) {
		this.a60 = a60;
	}

	public String getA61() {
		return this.a61;
	}

	public void setA61(String a61) {
		this.a61 = a61;
	}

	public String getA62() {
		return this.a62;
	}

	public void setA62(String a62) {
		this.a62 = a62;
	}

	public String getA63() {
		return this.a63;
	}

	public void setA63(String a63) {
		this.a63 = a63;
	}

	public String getA64() {
		return this.a64;
	}

	public void setA64(String a64) {
		this.a64 = a64;
	}

	public String getA65() {
		return this.a65;
	}

	public void setA65(String a65) {
		this.a65 = a65;
	}

	public String getA66() {
		return this.a66;
	}

	public void setA66(String a66) {
		this.a66 = a66;
	}

	public String getA67() {
		return this.a67;
	}

	public void setA67(String a67) {
		this.a67 = a67;
	}

	public String getA68() {
		return this.a68;
	}

	public void setA68(String a68) {
		this.a68 = a68;
	}

	public String getA69() {
		return this.a69;
	}

	public void setA69(String a69) {
		this.a69 = a69;
	}

	public String getA7() {
		return this.a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA70() {
		return this.a70;
	}

	public void setA70(String a70) {
		this.a70 = a70;
	}

	public String getA71() {
		return this.a71;
	}

	public void setA71(String a71) {
		this.a71 = a71;
	}

	public String getA72() {
		return this.a72;
	}

	public void setA72(String a72) {
		this.a72 = a72;
	}

	public String getA73() {
		return this.a73;
	}

	public void setA73(String a73) {
		this.a73 = a73;
	}

	public String getA74() {
		return this.a74;
	}

	public void setA74(String a74) {
		this.a74 = a74;
	}

	public String getA75() {
		return this.a75;
	}

	public void setA75(String a75) {
		this.a75 = a75;
	}

	public String getA76() {
		return this.a76;
	}

	public void setA76(String a76) {
		this.a76 = a76;
	}

	public String getA77() {
		return this.a77;
	}

	public void setA77(String a77) {
		this.a77 = a77;
	}

	public String getA78() {
		return this.a78;
	}

	public void setA78(String a78) {
		this.a78 = a78;
	}

	public String getA79() {
		return this.a79;
	}

	public void setA79(String a79) {
		this.a79 = a79;
	}

	public String getA8() {
		return this.a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA80() {
		return this.a80;
	}

	public void setA80(String a80) {
		this.a80 = a80;
	}

	public String getA81() {
		return this.a81;
	}

	public void setA81(String a81) {
		this.a81 = a81;
	}

	public String getA82() {
		return this.a82;
	}

	public void setA82(String a82) {
		this.a82 = a82;
	}

	public String getA83() {
		return this.a83;
	}

	public void setA83(String a83) {
		this.a83 = a83;
	}

	public String getA84() {
		return this.a84;
	}

	public void setA84(String a84) {
		this.a84 = a84;
	}

	public String getA85() {
		return this.a85;
	}

	public void setA85(String a85) {
		this.a85 = a85;
	}

	public String getA86() {
		return this.a86;
	}

	public void setA86(String a86) {
		this.a86 = a86;
	}

	public String getA87() {
		return this.a87;
	}

	public void setA87(String a87) {
		this.a87 = a87;
	}

	public String getA88() {
		return this.a88;
	}

	public void setA88(String a88) {
		this.a88 = a88;
	}

	public String getA89() {
		return this.a89;
	}

	public void setA89(String a89) {
		this.a89 = a89;
	}

	public String getA9() {
		return this.a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA90() {
		return this.a90;
	}

	public void setA90(String a90) {
		this.a90 = a90;
	}

	public String getA91() {
		return this.a91;
	}

	public void setA91(String a91) {
		this.a91 = a91;
	}

	public String getA92() {
		return this.a92;
	}

	public void setA92(String a92) {
		this.a92 = a92;
	}

	public String getA93() {
		return this.a93;
	}

	public void setA93(String a93) {
		this.a93 = a93;
	}

	public String getA94() {
		return this.a94;
	}

	public void setA94(String a94) {
		this.a94 = a94;
	}

	public String getA95() {
		return this.a95;
	}

	public void setA95(String a95) {
		this.a95 = a95;
	}

	public String getA96() {
		return this.a96;
	}

	public void setA96(String a96) {
		this.a96 = a96;
	}

	public String getA97() {
		return this.a97;
	}

	public void setA97(String a97) {
		this.a97 = a97;
	}

	public String getA98() {
		return this.a98;
	}

	public void setA98(String a98) {
		this.a98 = a98;
	}

	public String getA99() {
		return this.a99;
	}

	public void setA99(String a99) {
		this.a99 = a99;
	}

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetMeasureObserSeqId() {
		return this.assetMeasureObserSeqId;
	}

	public void setAssetMeasureObserSeqId(String assetMeasureObserSeqId) {
		this.assetMeasureObserSeqId = assetMeasureObserSeqId;
	}

	public String getAssetScheduleHistoryId() {
		return this.assetScheduleHistoryId;
	}

	public void setAssetScheduleHistoryId(String assetScheduleHistoryId) {
		this.assetScheduleHistoryId = assetScheduleHistoryId;
	}

	public String getAssetType() {
		return this.assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getCreatedStamp() {
		return this.createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return this.createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getDetailsOfMaint() {
		return this.detailsOfMaint;
	}

	public void setDetailsOfMaint(String detailsOfMaint) {
		this.detailsOfMaint = detailsOfMaint;
	}

	public Timestamp getDeviceCreatedStamp() {
		return this.deviceCreatedStamp;
	}

	public void setDeviceCreatedStamp(Timestamp deviceCreatedStamp) {
		this.deviceCreatedStamp = deviceCreatedStamp;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Timestamp getDeviceLastUpdatedStamp() {
		return this.deviceLastUpdatedStamp;
	}

	public void setDeviceLastUpdatedStamp(Timestamp deviceLastUpdatedStamp) {
		this.deviceLastUpdatedStamp = deviceLastUpdatedStamp;
	}

	public String getDeviceSeqId() {
		return this.deviceSeqId;
	}

	public void setDeviceSeqId(String deviceSeqId) {
		this.deviceSeqId = deviceSeqId;
	}

	public String getDoneBy() {
		return this.doneBy;
	}

	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getInitialOfIncharge() {
		return this.initialOfIncharge;
	}

	public void setInitialOfIncharge(String initialOfIncharge) {
		this.initialOfIncharge = initialOfIncharge;
	}

	public Timestamp getLastUpdatedStamp() {
		return this.lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return this.lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getM1() {
		return this.m1;
	}

	public void setM1(String m1) {
		this.m1 = m1;
	}

	public String getM10() {
		return this.m10;
	}

	public void setM10(String m10) {
		this.m10 = m10;
	}

	public String getM11() {
		return this.m11;
	}

	public void setM11(String m11) {
		this.m11 = m11;
	}

	public String getM12() {
		return this.m12;
	}

	public void setM12(String m12) {
		this.m12 = m12;
	}

	public String getM13() {
		return this.m13;
	}

	public void setM13(String m13) {
		this.m13 = m13;
	}

	public String getM14() {
		return this.m14;
	}

	public void setM14(String m14) {
		this.m14 = m14;
	}

	public String getM15() {
		return this.m15;
	}

	public void setM15(String m15) {
		this.m15 = m15;
	}

	public String getM16() {
		return this.m16;
	}

	public void setM16(String m16) {
		this.m16 = m16;
	}

	public String getM17() {
		return this.m17;
	}

	public void setM17(String m17) {
		this.m17 = m17;
	}

	public String getM18() {
		return this.m18;
	}

	public void setM18(String m18) {
		this.m18 = m18;
	}

	public String getM19() {
		return this.m19;
	}

	public void setM19(String m19) {
		this.m19 = m19;
	}

	public String getM2() {
		return this.m2;
	}

	public void setM2(String m2) {
		this.m2 = m2;
	}

	public String getM20() {
		return this.m20;
	}

	public void setM20(String m20) {
		this.m20 = m20;
	}

	public String getM21() {
		return this.m21;
	}

	public void setM21(String m21) {
		this.m21 = m21;
	}

	public String getM22() {
		return this.m22;
	}

	public void setM22(String m22) {
		this.m22 = m22;
	}

	public String getM23() {
		return this.m23;
	}

	public void setM23(String m23) {
		this.m23 = m23;
	}

	public String getM24() {
		return this.m24;
	}

	public void setM24(String m24) {
		this.m24 = m24;
	}

	public String getM25() {
		return this.m25;
	}

	public void setM25(String m25) {
		this.m25 = m25;
	}

	public String getM26() {
		return this.m26;
	}

	public void setM26(String m26) {
		this.m26 = m26;
	}

	public String getM27() {
		return this.m27;
	}

	public void setM27(String m27) {
		this.m27 = m27;
	}

	public String getM28() {
		return this.m28;
	}

	public void setM28(String m28) {
		this.m28 = m28;
	}

	public String getM29() {
		return this.m29;
	}

	public void setM29(String m29) {
		this.m29 = m29;
	}

	public String getM3() {
		return this.m3;
	}

	public void setM3(String m3) {
		this.m3 = m3;
	}

	public String getM30() {
		return this.m30;
	}

	public void setM30(String m30) {
		this.m30 = m30;
	}

	public String getM31() {
		return this.m31;
	}

	public void setM31(String m31) {
		this.m31 = m31;
	}

	public String getM32() {
		return this.m32;
	}

	public void setM32(String m32) {
		this.m32 = m32;
	}

	public String getM33() {
		return this.m33;
	}

	public void setM33(String m33) {
		this.m33 = m33;
	}

	public String getM34() {
		return this.m34;
	}

	public void setM34(String m34) {
		this.m34 = m34;
	}

	public String getM35() {
		return this.m35;
	}

	public void setM35(String m35) {
		this.m35 = m35;
	}

	public String getM36() {
		return this.m36;
	}

	public void setM36(String m36) {
		this.m36 = m36;
	}

	public String getM37() {
		return this.m37;
	}

	public void setM37(String m37) {
		this.m37 = m37;
	}

	public String getM38() {
		return this.m38;
	}

	public void setM38(String m38) {
		this.m38 = m38;
	}

	public String getM39() {
		return this.m39;
	}

	public void setM39(String m39) {
		this.m39 = m39;
	}

	public String getM4() {
		return this.m4;
	}

	public void setM4(String m4) {
		this.m4 = m4;
	}

	public String getM40() {
		return this.m40;
	}

	public void setM40(String m40) {
		this.m40 = m40;
	}

	public String getM41() {
		return this.m41;
	}

	public void setM41(String m41) {
		this.m41 = m41;
	}

	public String getM42() {
		return this.m42;
	}

	public void setM42(String m42) {
		this.m42 = m42;
	}

	public String getM43() {
		return this.m43;
	}

	public void setM43(String m43) {
		this.m43 = m43;
	}

	public String getM44() {
		return this.m44;
	}

	public void setM44(String m44) {
		this.m44 = m44;
	}

	public String getM45() {
		return this.m45;
	}

	public void setM45(String m45) {
		this.m45 = m45;
	}

	public String getM46() {
		return this.m46;
	}

	public void setM46(String m46) {
		this.m46 = m46;
	}

	public String getM47() {
		return this.m47;
	}

	public void setM47(String m47) {
		this.m47 = m47;
	}

	public String getM48() {
		return this.m48;
	}

	public void setM48(String m48) {
		this.m48 = m48;
	}

	public String getM49() {
		return this.m49;
	}

	public void setM49(String m49) {
		this.m49 = m49;
	}

	public String getM5() {
		return this.m5;
	}

	public void setM5(String m5) {
		this.m5 = m5;
	}

	public String getM50() {
		return this.m50;
	}

	public void setM50(String m50) {
		this.m50 = m50;
	}

	public String getM51() {
		return this.m51;
	}

	public void setM51(String m51) {
		this.m51 = m51;
	}

	public String getM52() {
		return this.m52;
	}

	public void setM52(String m52) {
		this.m52 = m52;
	}

	public String getM53() {
		return this.m53;
	}

	public void setM53(String m53) {
		this.m53 = m53;
	}

	public String getM54() {
		return this.m54;
	}

	public void setM54(String m54) {
		this.m54 = m54;
	}

	public String getM55() {
		return this.m55;
	}

	public void setM55(String m55) {
		this.m55 = m55;
	}

	public String getM56() {
		return this.m56;
	}

	public void setM56(String m56) {
		this.m56 = m56;
	}

	public String getM57() {
		return this.m57;
	}

	public void setM57(String m57) {
		this.m57 = m57;
	}

	public String getM58() {
		return this.m58;
	}

	public void setM58(String m58) {
		this.m58 = m58;
	}

	public String getM59() {
		return this.m59;
	}

	public void setM59(String m59) {
		this.m59 = m59;
	}

	public String getM6() {
		return this.m6;
	}

	public void setM6(String m6) {
		this.m6 = m6;
	}

	public String getM60() {
		return this.m60;
	}

	public void setM60(String m60) {
		this.m60 = m60;
	}

	public String getM61() {
		return this.m61;
	}

	public void setM61(String m61) {
		this.m61 = m61;
	}

	public String getM62() {
		return this.m62;
	}

	public void setM62(String m62) {
		this.m62 = m62;
	}

	public String getM63() {
		return this.m63;
	}

	public void setM63(String m63) {
		this.m63 = m63;
	}

	public String getM64() {
		return this.m64;
	}

	public void setM64(String m64) {
		this.m64 = m64;
	}

	public String getM65() {
		return this.m65;
	}

	public void setM65(String m65) {
		this.m65 = m65;
	}

	public String getM66() {
		return this.m66;
	}

	public void setM66(String m66) {
		this.m66 = m66;
	}

	public String getM67() {
		return this.m67;
	}

	public void setM67(String m67) {
		this.m67 = m67;
	}

	public String getM68() {
		return this.m68;
	}

	public void setM68(String m68) {
		this.m68 = m68;
	}

	public String getM69() {
		return this.m69;
	}

	public void setM69(String m69) {
		this.m69 = m69;
	}

	public String getM7() {
		return this.m7;
	}

	public void setM7(String m7) {
		this.m7 = m7;
	}

	public String getM70() {
		return this.m70;
	}

	public void setM70(String m70) {
		this.m70 = m70;
	}

	public String getM8() {
		return this.m8;
	}

	public void setM8(String m8) {
		this.m8 = m8;
	}

	public String getM9() {
		return this.m9;
	}

	public void setM9(String m9) {
		this.m9 = m9;
	}

	public String getMake() {
		return this.make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getMma1() {
		return this.mma1;
	}

	public void setMma1(String mma1) {
		this.mma1 = mma1;
	}

	public String getMma10() {
		return this.mma10;
	}

	public void setMma10(String mma10) {
		this.mma10 = mma10;
	}

	public String getMma2() {
		return this.mma2;
	}

	public void setMma2(String mma2) {
		this.mma2 = mma2;
	}

	public String getMma3() {
		return this.mma3;
	}

	public void setMma3(String mma3) {
		this.mma3 = mma3;
	}

	public String getMma4() {
		return this.mma4;
	}

	public void setMma4(String mma4) {
		this.mma4 = mma4;
	}

	public String getMma5() {
		return this.mma5;
	}

	public void setMma5(String mma5) {
		this.mma5 = mma5;
	}

	public String getMma6() {
		return this.mma6;
	}

	public void setMma6(String mma6) {
		this.mma6 = mma6;
	}

	public String getMma7() {
		return this.mma7;
	}

	public void setMma7(String mma7) {
		this.mma7 = mma7;
	}

	public String getMma8() {
		return this.mma8;
	}

	public void setMma8(String mma8) {
		this.mma8 = mma8;
	}

	public String getMma9() {
		return this.mma9;
	}

	public void setMma9(String mma9) {
		this.mma9 = mma9;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getScheduleActualDate() {
		return this.scheduleActualDate;
	}

	public void setScheduleActualDate(Timestamp scheduleActualDate) {
		this.scheduleActualDate = scheduleActualDate;
	}

	public String getScheduleCode() {
		return this.scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public Timestamp getScheduleDate() {
		return this.scheduleDate;
	}

	public void setScheduleDate(Timestamp scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
