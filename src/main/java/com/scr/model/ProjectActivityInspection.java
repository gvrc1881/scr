package com.scr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "project_activity_inspection")
@NamedQuery(name = "ProjectActivityInspection.findAll", query = "SELECT  pai from ProjectActivityInspection pai")
public class ProjectActivityInspection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String status;
	
	private String division;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private String location;
	
	private String make;
	
	private String model;
	
	private String assetId;
	
	private String doneBy;
	
	private String approved;
	
	private String approvedBy;
	
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
	
	@Column(columnDefinition="TEXT")
	private String mm1;

	@Column(columnDefinition="TEXT")
	private String mm10;

	@Column(columnDefinition="TEXT")
	private String mm2;

	@Column(columnDefinition="TEXT")
	private String mm3;

	@Column(columnDefinition="TEXT")
	private String mm4;

	@Column(columnDefinition="TEXT")
	private String mm5;

	@Column(columnDefinition="TEXT")
	private String mm6;

	@Column(columnDefinition="TEXT")
	private String mm7;

	@Column(columnDefinition="TEXT")
	private String mm8;

	@Column(columnDefinition="TEXT")
	private String mm9;
	
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
	
	private int inspectionVersionNo;
	
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "activity_id", foreignKey = @ForeignKey(name = "fk_prj_act_ins_work_phase_id"))
	private WorkPhaseActivity activityId;
	
	@ManyToOne
	@JoinColumn(name = "facility_id", foreignKey = @ForeignKey(name = "fk_prj_act_ins_facility"))
	private Facility facilityId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getDoneBy() {
		return doneBy;
	}

	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getM1() {
		return m1;
	}

	public void setM1(String m1) {
		this.m1 = m1;
	}

	public String getM10() {
		return m10;
	}

	public void setM10(String m10) {
		this.m10 = m10;
	}

	public String getM11() {
		return m11;
	}

	public void setM11(String m11) {
		this.m11 = m11;
	}

	public String getM12() {
		return m12;
	}

	public void setM12(String m12) {
		this.m12 = m12;
	}

	public String getM13() {
		return m13;
	}

	public void setM13(String m13) {
		this.m13 = m13;
	}

	public String getM14() {
		return m14;
	}

	public void setM14(String m14) {
		this.m14 = m14;
	}

	public String getM15() {
		return m15;
	}

	public void setM15(String m15) {
		this.m15 = m15;
	}

	public String getM16() {
		return m16;
	}

	public void setM16(String m16) {
		this.m16 = m16;
	}

	public String getM17() {
		return m17;
	}

	public void setM17(String m17) {
		this.m17 = m17;
	}

	public String getM18() {
		return m18;
	}

	public void setM18(String m18) {
		this.m18 = m18;
	}

	public String getM19() {
		return m19;
	}

	public void setM19(String m19) {
		this.m19 = m19;
	}

	public String getM2() {
		return m2;
	}

	public void setM2(String m2) {
		this.m2 = m2;
	}

	public String getM20() {
		return m20;
	}

	public void setM20(String m20) {
		this.m20 = m20;
	}

	public String getM21() {
		return m21;
	}

	public void setM21(String m21) {
		this.m21 = m21;
	}

	public String getM22() {
		return m22;
	}

	public void setM22(String m22) {
		this.m22 = m22;
	}

	public String getM23() {
		return m23;
	}

	public void setM23(String m23) {
		this.m23 = m23;
	}

	public String getM24() {
		return m24;
	}

	public void setM24(String m24) {
		this.m24 = m24;
	}

	public String getM25() {
		return m25;
	}

	public void setM25(String m25) {
		this.m25 = m25;
	}

	public String getM26() {
		return m26;
	}

	public void setM26(String m26) {
		this.m26 = m26;
	}

	public String getM27() {
		return m27;
	}

	public void setM27(String m27) {
		this.m27 = m27;
	}

	public String getM28() {
		return m28;
	}

	public void setM28(String m28) {
		this.m28 = m28;
	}

	public String getM29() {
		return m29;
	}

	public void setM29(String m29) {
		this.m29 = m29;
	}

	public String getM3() {
		return m3;
	}

	public void setM3(String m3) {
		this.m3 = m3;
	}

	public String getM30() {
		return m30;
	}

	public void setM30(String m30) {
		this.m30 = m30;
	}

	public String getM31() {
		return m31;
	}

	public void setM31(String m31) {
		this.m31 = m31;
	}

	public String getM32() {
		return m32;
	}

	public void setM32(String m32) {
		this.m32 = m32;
	}

	public String getM33() {
		return m33;
	}

	public void setM33(String m33) {
		this.m33 = m33;
	}

	public String getM34() {
		return m34;
	}

	public void setM34(String m34) {
		this.m34 = m34;
	}

	public String getM35() {
		return m35;
	}

	public void setM35(String m35) {
		this.m35 = m35;
	}

	public String getM36() {
		return m36;
	}

	public void setM36(String m36) {
		this.m36 = m36;
	}

	public String getM37() {
		return m37;
	}

	public void setM37(String m37) {
		this.m37 = m37;
	}

	public String getM38() {
		return m38;
	}

	public void setM38(String m38) {
		this.m38 = m38;
	}

	public String getM39() {
		return m39;
	}

	public void setM39(String m39) {
		this.m39 = m39;
	}

	public String getM4() {
		return m4;
	}

	public void setM4(String m4) {
		this.m4 = m4;
	}

	public String getM40() {
		return m40;
	}

	public void setM40(String m40) {
		this.m40 = m40;
	}

	public String getM41() {
		return m41;
	}

	public void setM41(String m41) {
		this.m41 = m41;
	}

	public String getM42() {
		return m42;
	}

	public void setM42(String m42) {
		this.m42 = m42;
	}

	public String getM43() {
		return m43;
	}

	public void setM43(String m43) {
		this.m43 = m43;
	}

	public String getM44() {
		return m44;
	}

	public void setM44(String m44) {
		this.m44 = m44;
	}

	public String getM45() {
		return m45;
	}

	public void setM45(String m45) {
		this.m45 = m45;
	}

	public String getM46() {
		return m46;
	}

	public void setM46(String m46) {
		this.m46 = m46;
	}

	public String getM47() {
		return m47;
	}

	public void setM47(String m47) {
		this.m47 = m47;
	}

	public String getM48() {
		return m48;
	}

	public void setM48(String m48) {
		this.m48 = m48;
	}

	public String getM49() {
		return m49;
	}

	public void setM49(String m49) {
		this.m49 = m49;
	}

	public String getM5() {
		return m5;
	}

	public void setM5(String m5) {
		this.m5 = m5;
	}

	public String getM50() {
		return m50;
	}

	public void setM50(String m50) {
		this.m50 = m50;
	}

	public String getM51() {
		return m51;
	}

	public void setM51(String m51) {
		this.m51 = m51;
	}

	public String getM52() {
		return m52;
	}

	public void setM52(String m52) {
		this.m52 = m52;
	}

	public String getM53() {
		return m53;
	}

	public void setM53(String m53) {
		this.m53 = m53;
	}

	public String getM54() {
		return m54;
	}

	public void setM54(String m54) {
		this.m54 = m54;
	}

	public String getM55() {
		return m55;
	}

	public void setM55(String m55) {
		this.m55 = m55;
	}

	public String getM56() {
		return m56;
	}

	public void setM56(String m56) {
		this.m56 = m56;
	}

	public String getM57() {
		return m57;
	}

	public void setM57(String m57) {
		this.m57 = m57;
	}

	public String getM58() {
		return m58;
	}

	public void setM58(String m58) {
		this.m58 = m58;
	}

	public String getM59() {
		return m59;
	}

	public void setM59(String m59) {
		this.m59 = m59;
	}

	public String getM6() {
		return m6;
	}

	public void setM6(String m6) {
		this.m6 = m6;
	}

	public String getM60() {
		return m60;
	}

	public void setM60(String m60) {
		this.m60 = m60;
	}

	public String getM61() {
		return m61;
	}

	public void setM61(String m61) {
		this.m61 = m61;
	}

	public String getM62() {
		return m62;
	}

	public void setM62(String m62) {
		this.m62 = m62;
	}

	public String getM63() {
		return m63;
	}

	public void setM63(String m63) {
		this.m63 = m63;
	}

	public String getM64() {
		return m64;
	}

	public void setM64(String m64) {
		this.m64 = m64;
	}

	public String getM65() {
		return m65;
	}

	public void setM65(String m65) {
		this.m65 = m65;
	}

	public String getM66() {
		return m66;
	}

	public void setM66(String m66) {
		this.m66 = m66;
	}

	public String getM67() {
		return m67;
	}

	public void setM67(String m67) {
		this.m67 = m67;
	}

	public String getM68() {
		return m68;
	}

	public void setM68(String m68) {
		this.m68 = m68;
	}

	public String getM69() {
		return m69;
	}

	public void setM69(String m69) {
		this.m69 = m69;
	}

	public String getM7() {
		return m7;
	}

	public void setM7(String m7) {
		this.m7 = m7;
	}

	public String getM70() {
		return m70;
	}

	public void setM70(String m70) {
		this.m70 = m70;
	}

	public String getM8() {
		return m8;
	}

	public void setM8(String m8) {
		this.m8 = m8;
	}

	public String getM9() {
		return m9;
	}

	public void setM9(String m9) {
		this.m9 = m9;
	}

	public String getMma1() {
		return mma1;
	}

	public void setMma1(String mma1) {
		this.mma1 = mma1;
	}

	public String getMma10() {
		return mma10;
	}

	public void setMma10(String mma10) {
		this.mma10 = mma10;
	}

	public String getMma2() {
		return mma2;
	}

	public void setMma2(String mma2) {
		this.mma2 = mma2;
	}

	public String getMma3() {
		return mma3;
	}

	public void setMma3(String mma3) {
		this.mma3 = mma3;
	}

	public String getMma4() {
		return mma4;
	}

	public void setMma4(String mma4) {
		this.mma4 = mma4;
	}

	public String getMma5() {
		return mma5;
	}

	public void setMma5(String mma5) {
		this.mma5 = mma5;
	}

	public String getMma6() {
		return mma6;
	}

	public void setMma6(String mma6) {
		this.mma6 = mma6;
	}

	public String getMma7() {
		return mma7;
	}

	public void setMma7(String mma7) {
		this.mma7 = mma7;
	}

	public String getMma8() {
		return mma8;
	}

	public void setMma8(String mma8) {
		this.mma8 = mma8;
	}

	public String getMma9() {
		return mma9;
	}

	public void setMma9(String mma9) {
		this.mma9 = mma9;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA10() {
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA100() {
		return a100;
	}

	public void setA100(String a100) {
		this.a100 = a100;
	}

	public String getA101() {
		return a101;
	}

	public void setA101(String a101) {
		this.a101 = a101;
	}

	public String getA102() {
		return a102;
	}

	public void setA102(String a102) {
		this.a102 = a102;
	}

	public String getA103() {
		return a103;
	}

	public void setA103(String a103) {
		this.a103 = a103;
	}

	public String getA104() {
		return a104;
	}

	public void setA104(String a104) {
		this.a104 = a104;
	}

	public String getA105() {
		return a105;
	}

	public void setA105(String a105) {
		this.a105 = a105;
	}

	public String getA106() {
		return a106;
	}

	public void setA106(String a106) {
		this.a106 = a106;
	}

	public String getA107() {
		return a107;
	}

	public void setA107(String a107) {
		this.a107 = a107;
	}

	public String getA108() {
		return a108;
	}

	public void setA108(String a108) {
		this.a108 = a108;
	}

	public String getA109() {
		return a109;
	}

	public void setA109(String a109) {
		this.a109 = a109;
	}

	public String getA11() {
		return a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getA110() {
		return a110;
	}

	public void setA110(String a110) {
		this.a110 = a110;
	}

	public String getA111() {
		return a111;
	}

	public void setA111(String a111) {
		this.a111 = a111;
	}

	public String getA112() {
		return a112;
	}

	public void setA112(String a112) {
		this.a112 = a112;
	}

	public String getA113() {
		return a113;
	}

	public void setA113(String a113) {
		this.a113 = a113;
	}

	public String getA114() {
		return a114;
	}

	public void setA114(String a114) {
		this.a114 = a114;
	}

	public String getA115() {
		return a115;
	}

	public void setA115(String a115) {
		this.a115 = a115;
	}

	public String getA116() {
		return a116;
	}

	public void setA116(String a116) {
		this.a116 = a116;
	}

	public String getA117() {
		return a117;
	}

	public void setA117(String a117) {
		this.a117 = a117;
	}

	public String getA118() {
		return a118;
	}

	public void setA118(String a118) {
		this.a118 = a118;
	}

	public String getA119() {
		return a119;
	}

	public void setA119(String a119) {
		this.a119 = a119;
	}

	public String getA12() {
		return a12;
	}

	public void setA12(String a12) {
		this.a12 = a12;
	}

	public String getA120() {
		return a120;
	}

	public void setA120(String a120) {
		this.a120 = a120;
	}

	public String getA121() {
		return a121;
	}

	public void setA121(String a121) {
		this.a121 = a121;
	}

	public String getA122() {
		return a122;
	}

	public void setA122(String a122) {
		this.a122 = a122;
	}

	public String getA123() {
		return a123;
	}

	public void setA123(String a123) {
		this.a123 = a123;
	}

	public String getA124() {
		return a124;
	}

	public void setA124(String a124) {
		this.a124 = a124;
	}

	public String getA125() {
		return a125;
	}

	public void setA125(String a125) {
		this.a125 = a125;
	}

	public String getA126() {
		return a126;
	}

	public void setA126(String a126) {
		this.a126 = a126;
	}

	public String getA127() {
		return a127;
	}

	public void setA127(String a127) {
		this.a127 = a127;
	}

	public String getA128() {
		return a128;
	}

	public void setA128(String a128) {
		this.a128 = a128;
	}

	public String getA129() {
		return a129;
	}

	public void setA129(String a129) {
		this.a129 = a129;
	}

	public String getA13() {
		return a13;
	}

	public void setA13(String a13) {
		this.a13 = a13;
	}

	public String getA130() {
		return a130;
	}

	public void setA130(String a130) {
		this.a130 = a130;
	}

	public String getA14() {
		return a14;
	}

	public void setA14(String a14) {
		this.a14 = a14;
	}

	public String getA15() {
		return a15;
	}

	public void setA15(String a15) {
		this.a15 = a15;
	}

	public String getA16() {
		return a16;
	}

	public void setA16(String a16) {
		this.a16 = a16;
	}

	public String getA17() {
		return a17;
	}

	public void setA17(String a17) {
		this.a17 = a17;
	}

	public String getA18() {
		return a18;
	}

	public void setA18(String a18) {
		this.a18 = a18;
	}

	public String getA19() {
		return a19;
	}

	public void setA19(String a19) {
		this.a19 = a19;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA20() {
		return a20;
	}

	public void setA20(String a20) {
		this.a20 = a20;
	}

	public String getA21() {
		return a21;
	}

	public void setA21(String a21) {
		this.a21 = a21;
	}

	public String getA22() {
		return a22;
	}

	public void setA22(String a22) {
		this.a22 = a22;
	}

	public String getA23() {
		return a23;
	}

	public void setA23(String a23) {
		this.a23 = a23;
	}

	public String getA24() {
		return a24;
	}

	public void setA24(String a24) {
		this.a24 = a24;
	}

	public String getA25() {
		return a25;
	}

	public void setA25(String a25) {
		this.a25 = a25;
	}

	public String getA26() {
		return a26;
	}

	public void setA26(String a26) {
		this.a26 = a26;
	}

	public String getA27() {
		return a27;
	}

	public void setA27(String a27) {
		this.a27 = a27;
	}

	public String getA28() {
		return a28;
	}

	public void setA28(String a28) {
		this.a28 = a28;
	}

	public String getA29() {
		return a29;
	}

	public void setA29(String a29) {
		this.a29 = a29;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA30() {
		return a30;
	}

	public void setA30(String a30) {
		this.a30 = a30;
	}

	public String getA31() {
		return a31;
	}

	public void setA31(String a31) {
		this.a31 = a31;
	}

	public String getA32() {
		return a32;
	}

	public void setA32(String a32) {
		this.a32 = a32;
	}

	public String getA33() {
		return a33;
	}

	public void setA33(String a33) {
		this.a33 = a33;
	}

	public String getA34() {
		return a34;
	}

	public void setA34(String a34) {
		this.a34 = a34;
	}

	public String getA35() {
		return a35;
	}

	public void setA35(String a35) {
		this.a35 = a35;
	}

	public String getA36() {
		return a36;
	}

	public void setA36(String a36) {
		this.a36 = a36;
	}

	public String getA37() {
		return a37;
	}

	public void setA37(String a37) {
		this.a37 = a37;
	}

	public String getA38() {
		return a38;
	}

	public void setA38(String a38) {
		this.a38 = a38;
	}

	public String getA39() {
		return a39;
	}

	public void setA39(String a39) {
		this.a39 = a39;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA40() {
		return a40;
	}

	public void setA40(String a40) {
		this.a40 = a40;
	}

	public String getA41() {
		return a41;
	}

	public void setA41(String a41) {
		this.a41 = a41;
	}

	public String getA42() {
		return a42;
	}

	public void setA42(String a42) {
		this.a42 = a42;
	}

	public String getA43() {
		return a43;
	}

	public void setA43(String a43) {
		this.a43 = a43;
	}

	public String getA44() {
		return a44;
	}

	public void setA44(String a44) {
		this.a44 = a44;
	}

	public String getA45() {
		return a45;
	}

	public void setA45(String a45) {
		this.a45 = a45;
	}

	public String getA46() {
		return a46;
	}

	public void setA46(String a46) {
		this.a46 = a46;
	}

	public String getA47() {
		return a47;
	}

	public void setA47(String a47) {
		this.a47 = a47;
	}

	public String getA48() {
		return a48;
	}

	public void setA48(String a48) {
		this.a48 = a48;
	}

	public String getA49() {
		return a49;
	}

	public void setA49(String a49) {
		this.a49 = a49;
	}

	public String getA5() {
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getA50() {
		return a50;
	}

	public void setA50(String a50) {
		this.a50 = a50;
	}

	public String getA51() {
		return a51;
	}

	public void setA51(String a51) {
		this.a51 = a51;
	}

	public String getA52() {
		return a52;
	}

	public void setA52(String a52) {
		this.a52 = a52;
	}

	public String getA53() {
		return a53;
	}

	public void setA53(String a53) {
		this.a53 = a53;
	}

	public String getA54() {
		return a54;
	}

	public void setA54(String a54) {
		this.a54 = a54;
	}

	public String getA55() {
		return a55;
	}

	public void setA55(String a55) {
		this.a55 = a55;
	}

	public String getA56() {
		return a56;
	}

	public void setA56(String a56) {
		this.a56 = a56;
	}

	public String getA57() {
		return a57;
	}

	public void setA57(String a57) {
		this.a57 = a57;
	}

	public String getA58() {
		return a58;
	}

	public void setA58(String a58) {
		this.a58 = a58;
	}

	public String getA59() {
		return a59;
	}

	public void setA59(String a59) {
		this.a59 = a59;
	}

	public String getA6() {
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA60() {
		return a60;
	}

	public void setA60(String a60) {
		this.a60 = a60;
	}

	public String getA61() {
		return a61;
	}

	public void setA61(String a61) {
		this.a61 = a61;
	}

	public String getA62() {
		return a62;
	}

	public void setA62(String a62) {
		this.a62 = a62;
	}

	public String getA63() {
		return a63;
	}

	public void setA63(String a63) {
		this.a63 = a63;
	}

	public String getA64() {
		return a64;
	}

	public void setA64(String a64) {
		this.a64 = a64;
	}

	public String getA65() {
		return a65;
	}

	public void setA65(String a65) {
		this.a65 = a65;
	}

	public String getA66() {
		return a66;
	}

	public void setA66(String a66) {
		this.a66 = a66;
	}

	public String getA67() {
		return a67;
	}

	public void setA67(String a67) {
		this.a67 = a67;
	}

	public String getA68() {
		return a68;
	}

	public void setA68(String a68) {
		this.a68 = a68;
	}

	public String getA69() {
		return a69;
	}

	public void setA69(String a69) {
		this.a69 = a69;
	}

	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA70() {
		return a70;
	}

	public void setA70(String a70) {
		this.a70 = a70;
	}

	public String getA71() {
		return a71;
	}

	public void setA71(String a71) {
		this.a71 = a71;
	}

	public String getA72() {
		return a72;
	}

	public void setA72(String a72) {
		this.a72 = a72;
	}

	public String getA73() {
		return a73;
	}

	public void setA73(String a73) {
		this.a73 = a73;
	}

	public String getA74() {
		return a74;
	}

	public void setA74(String a74) {
		this.a74 = a74;
	}

	public String getA75() {
		return a75;
	}

	public void setA75(String a75) {
		this.a75 = a75;
	}

	public String getA76() {
		return a76;
	}

	public void setA76(String a76) {
		this.a76 = a76;
	}

	public String getA77() {
		return a77;
	}

	public void setA77(String a77) {
		this.a77 = a77;
	}

	public String getA78() {
		return a78;
	}

	public void setA78(String a78) {
		this.a78 = a78;
	}

	public String getA79() {
		return a79;
	}

	public void setA79(String a79) {
		this.a79 = a79;
	}

	public String getA8() {
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA80() {
		return a80;
	}

	public void setA80(String a80) {
		this.a80 = a80;
	}

	public String getA81() {
		return a81;
	}

	public void setA81(String a81) {
		this.a81 = a81;
	}

	public String getA82() {
		return a82;
	}

	public void setA82(String a82) {
		this.a82 = a82;
	}

	public String getA83() {
		return a83;
	}

	public void setA83(String a83) {
		this.a83 = a83;
	}

	public String getA84() {
		return a84;
	}

	public void setA84(String a84) {
		this.a84 = a84;
	}

	public String getA85() {
		return a85;
	}

	public void setA85(String a85) {
		this.a85 = a85;
	}

	public String getA86() {
		return a86;
	}

	public void setA86(String a86) {
		this.a86 = a86;
	}

	public String getA87() {
		return a87;
	}

	public void setA87(String a87) {
		this.a87 = a87;
	}

	public String getA88() {
		return a88;
	}

	public void setA88(String a88) {
		this.a88 = a88;
	}

	public String getA89() {
		return a89;
	}

	public void setA89(String a89) {
		this.a89 = a89;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA90() {
		return a90;
	}

	public void setA90(String a90) {
		this.a90 = a90;
	}

	public String getA91() {
		return a91;
	}

	public void setA91(String a91) {
		this.a91 = a91;
	}

	public String getA92() {
		return a92;
	}

	public void setA92(String a92) {
		this.a92 = a92;
	}

	public String getA93() {
		return a93;
	}

	public void setA93(String a93) {
		this.a93 = a93;
	}

	public String getA94() {
		return a94;
	}

	public void setA94(String a94) {
		this.a94 = a94;
	}

	public String getA95() {
		return a95;
	}

	public void setA95(String a95) {
		this.a95 = a95;
	}

	public String getA96() {
		return a96;
	}

	public void setA96(String a96) {
		this.a96 = a96;
	}

	public String getA97() {
		return a97;
	}

	public void setA97(String a97) {
		this.a97 = a97;
	}

	public String getA98() {
		return a98;
	}

	public void setA98(String a98) {
		this.a98 = a98;
	}

	public String getA99() {
		return a99;
	}

	public void setA99(String a99) {
		this.a99 = a99;
	}

	public int getInspectionVersionNo() {
		return inspectionVersionNo;
	}

	public void setInspectionVersionNo(int inspectionVersionNo) {
		this.inspectionVersionNo = inspectionVersionNo;
	}

	public Facility getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Facility facilityId) {
		this.facilityId = facilityId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WorkPhaseActivity getActivityId() {
		return activityId;
	}

	public void setActivityId(WorkPhaseActivity activityId) {
		this.activityId = activityId;
	}

	public String getMm1() {
		return mm1;
	}

	public void setMm1(String mm1) {
		this.mm1 = mm1;
	}

	public String getMm10() {
		return mm10;
	}

	public void setMm10(String mm10) {
		this.mm10 = mm10;
	}

	public String getMm2() {
		return mm2;
	}

	public void setMm2(String mm2) {
		this.mm2 = mm2;
	}

	public String getMm3() {
		return mm3;
	}

	public void setMm3(String mm3) {
		this.mm3 = mm3;
	}

	public String getMm4() {
		return mm4;
	}

	public void setMm4(String mm4) {
		this.mm4 = mm4;
	}

	public String getMm5() {
		return mm5;
	}

	public void setMm5(String mm5) {
		this.mm5 = mm5;
	}

	public String getMm6() {
		return mm6;
	}

	public void setMm6(String mm6) {
		this.mm6 = mm6;
	}

	public String getMm7() {
		return mm7;
	}

	public void setMm7(String mm7) {
		this.mm7 = mm7;
	}

	public String getMm8() {
		return mm8;
	}

	public void setMm8(String mm8) {
		this.mm8 = mm8;
	}

	public String getMm9() {
		return mm9;
	}

	public void setMm9(String mm9) {
		this.mm9 = mm9;
	}

	@Override
	public String toString() {
		return "ProjectActivityInspection [id=" + id + ", status=" + status + ", division=" + division + ", date="
				+ date + ", location=" + location + ", make=" + make + ", model=" + model + ", assetId=" + assetId
				+ ", doneBy=" + doneBy + ", approved=" + approved + ", approvedBy=" + approvedBy + ", m1=" + m1
				+ ", m10=" + m10 + ", m11=" + m11 + ", m12=" + m12 + ", m13=" + m13 + ", m14=" + m14 + ", m15=" + m15
				+ ", m16=" + m16 + ", m17=" + m17 + ", m18=" + m18 + ", m19=" + m19 + ", m2=" + m2 + ", m20=" + m20
				+ ", m21=" + m21 + ", m22=" + m22 + ", m23=" + m23 + ", m24=" + m24 + ", m25=" + m25 + ", m26=" + m26
				+ ", m27=" + m27 + ", m28=" + m28 + ", m29=" + m29 + ", m3=" + m3 + ", m30=" + m30 + ", m31=" + m31
				+ ", m32=" + m32 + ", m33=" + m33 + ", m34=" + m34 + ", m35=" + m35 + ", m36=" + m36 + ", m37=" + m37
				+ ", m38=" + m38 + ", m39=" + m39 + ", m4=" + m4 + ", m40=" + m40 + ", m41=" + m41 + ", m42=" + m42
				+ ", m43=" + m43 + ", m44=" + m44 + ", m45=" + m45 + ", m46=" + m46 + ", m47=" + m47 + ", m48=" + m48
				+ ", m49=" + m49 + ", m5=" + m5 + ", m50=" + m50 + ", m51=" + m51 + ", m52=" + m52 + ", m53=" + m53
				+ ", m54=" + m54 + ", m55=" + m55 + ", m56=" + m56 + ", m57=" + m57 + ", m58=" + m58 + ", m59=" + m59
				+ ", m6=" + m6 + ", m60=" + m60 + ", m61=" + m61 + ", m62=" + m62 + ", m63=" + m63 + ", m64=" + m64
				+ ", m65=" + m65 + ", m66=" + m66 + ", m67=" + m67 + ", m68=" + m68 + ", m69=" + m69 + ", m7=" + m7
				+ ", m70=" + m70 + ", m8=" + m8 + ", m9=" + m9 + ", mma1=" + mma1 + ", mma10=" + mma10 + ", mma2="
				+ mma2 + ", mma3=" + mma3 + ", mma4=" + mma4 + ", mma5=" + mma5 + ", mma6=" + mma6 + ", mma7=" + mma7
				+ ", mma8=" + mma8 + ", mma9=" + mma9 + ", a1=" + a1 + ", a10=" + a10 + ", a100=" + a100 + ", a101="
				+ a101 + ", a102=" + a102 + ", a103=" + a103 + ", a104=" + a104 + ", a105=" + a105 + ", a106=" + a106
				+ ", a107=" + a107 + ", a108=" + a108 + ", a109=" + a109 + ", a11=" + a11 + ", a110=" + a110 + ", a111="
				+ a111 + ", a112=" + a112 + ", a113=" + a113 + ", a114=" + a114 + ", a115=" + a115 + ", a116=" + a116
				+ ", a117=" + a117 + ", a118=" + a118 + ", a119=" + a119 + ", a12=" + a12 + ", a120=" + a120 + ", a121="
				+ a121 + ", a122=" + a122 + ", a123=" + a123 + ", a124=" + a124 + ", a125=" + a125 + ", a126=" + a126
				+ ", a127=" + a127 + ", a128=" + a128 + ", a129=" + a129 + ", a13=" + a13 + ", a130=" + a130 + ", a14="
				+ a14 + ", a15=" + a15 + ", a16=" + a16 + ", a17=" + a17 + ", a18=" + a18 + ", a19=" + a19 + ", a2="
				+ a2 + ", a20=" + a20 + ", a21=" + a21 + ", a22=" + a22 + ", a23=" + a23 + ", a24=" + a24 + ", a25="
				+ a25 + ", a26=" + a26 + ", a27=" + a27 + ", a28=" + a28 + ", a29=" + a29 + ", a3=" + a3 + ", a30="
				+ a30 + ", a31=" + a31 + ", a32=" + a32 + ", a33=" + a33 + ", a34=" + a34 + ", a35=" + a35 + ", a36="
				+ a36 + ", a37=" + a37 + ", a38=" + a38 + ", a39=" + a39 + ", a4=" + a4 + ", a40=" + a40 + ", a41="
				+ a41 + ", a42=" + a42 + ", a43=" + a43 + ", a44=" + a44 + ", a45=" + a45 + ", a46=" + a46 + ", a47="
				+ a47 + ", a48=" + a48 + ", a49=" + a49 + ", a5=" + a5 + ", a50=" + a50 + ", a51=" + a51 + ", a52="
				+ a52 + ", a53=" + a53 + ", a54=" + a54 + ", a55=" + a55 + ", a56=" + a56 + ", a57=" + a57 + ", a58="
				+ a58 + ", a59=" + a59 + ", a6=" + a6 + ", a60=" + a60 + ", a61=" + a61 + ", a62=" + a62 + ", a63="
				+ a63 + ", a64=" + a64 + ", a65=" + a65 + ", a66=" + a66 + ", a67=" + a67 + ", a68=" + a68 + ", a69="
				+ a69 + ", a7=" + a7 + ", a70=" + a70 + ", a71=" + a71 + ", a72=" + a72 + ", a73=" + a73 + ", a74="
				+ a74 + ", a75=" + a75 + ", a76=" + a76 + ", a77=" + a77 + ", a78=" + a78 + ", a79=" + a79 + ", a8="
				+ a8 + ", a80=" + a80 + ", a81=" + a81 + ", a82=" + a82 + ", a83=" + a83 + ", a84=" + a84 + ", a85="
				+ a85 + ", a86=" + a86 + ", a87=" + a87 + ", a88=" + a88 + ", a89=" + a89 + ", a9=" + a9 + ", a90="
				+ a90 + ", a91=" + a91 + ", a92=" + a92 + ", a93=" + a93 + ", a94=" + a94 + ", a95=" + a95 + ", a96="
				+ a96 + ", a97=" + a97 + ", a98=" + a98 + ", a99=" + a99 + ", inspectionVersionNo="
				+ inspectionVersionNo + ", remark=" + remark + ", activityId=" + activityId + ", facilityId="
				+ facilityId + "]";
	}



}
