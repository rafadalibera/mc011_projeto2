@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * %tmp0
  %tmp1 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp2 = call i32 (i8 *, ...)* @printf(i8 * %tmp1, i32 10)
  %tmp3 = load i32 * %tmp0
  ret i32 %tmp3
}
%class.good = type { i32, i32 }
define i32 @__func_good(%class.good * %this) {
  %tmp4 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp5 = call i32 (i8 *, ...)* @printf(i8 * %tmp4, i32 10)
  ret i32 0
}
%class.good2 = type { }
define i32 @__func_good2(%class.good2 * %this) {
  ret i32 0
}
%class.k = type { i32, i32 }
define i32 @__TestMethod_k(%class.k * %this) {
  %tmp6 = alloca i32
  %tmp7 = alloca %class.good
  %tmp8 = call i8 *  @malloc(i32 8)
  %tmp9 = bitcast i8 * %tmp8 to %class.good *
  store %class.good * %tmp9, %class.good * %tmp7
  %tmp10 = load %class.good * %tmp7
  %tmp11 = call i32  @__func_good(%class.good * %tmp10)
  store i32 %tmp11, i32 * %tmp6
  store i32 10, i32 * %tmp6
  %tmp12 = getelementptr %class.k * %this, i32 0, i32 0
  %tmp13 = load i32 * %tmp12
  ret i32 %tmp13
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
