@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * %tmp0
  %tmp1 = call i8 *  @malloc(i32 8)
  %tmp2 = bitcast i8 * %tmp1 to %class.good *
  %tmp3 = call i32  @__func_good(%class.good * %tmp2)
  %tmp4 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp5 = call i32 (i8 *, ...)* @printf(i8 * %tmp4, i32 %tmp3)
  %tmp6 = load i32 * %tmp0
  ret i32 %tmp6
}
%class.good = type { i32, i32 }
define i32 @__func_good(%class.good * %this) {
  %tmp7 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp8 = call i32 (i8 *, ...)* @printf(i8 * %tmp7, i32 999)
  ret i32 0
}
%class.good2 = type { }
define i32 @__func_good2(%class.good2 * %this) {
  ret i32 0
}
%class.k = type { i32, i32 }
define i32 @__TestMethod_k(%class.k * %this) {
  %tmp9 = alloca i32
  %tmp10 = alloca %class.good *
  %tmp11 = call i8 *  @malloc(i32 8)
  %tmp12 = bitcast i8 * %tmp11 to %class.good *
  store %class.good * %tmp12, %class.good * * %tmp10
  %tmp13 = load %class.good * * %tmp10
  %tmp14 = call i32  @__func_good(%class.good * %tmp13)
  store i32 %tmp14, i32 * %tmp9
  store i32 10, i32 * %tmp9
  %tmp15 = getelementptr %class.k * %this, i32 0, i32 0
  %tmp16 = load i32 * %tmp15
  ret i32 %tmp16
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
