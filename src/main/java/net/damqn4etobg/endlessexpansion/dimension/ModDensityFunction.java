package net.damqn4etobg.endlessexpansion.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import static net.minecraft.world.level.levelgen.DensityFunctions.zero;

public class ModDensityFunction {

    public static DensityFunction densityFunction = new DensityFunction() {
        @Override
        public double compute(FunctionContext pContext) {
            return 0;
        }

        @Override
        public void fillArray(double[] pArray, ContextProvider pContextProvider) {

        }

        @Override
        public DensityFunction mapAll(Visitor pVisitor) {
            return null;
        }

        @Override
        public double minValue() {
            return 0;
        }

        @Override
        public double maxValue() {
            return 0;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return null;
        }
    };

    public static DensityFunction modShiftedNoise2d(DensityFunction pShiftX, DensityFunction pShiftZ, double pXzScale, Holder<NormalNoise.NoiseParameters> pNoiseData) {
        return new ShiftedNoise(pShiftX, zero(), pShiftZ, pXzScale, 400.0D, new DensityFunction.NoiseHolder(pNoiseData));
    }

    protected record ShiftedNoise(DensityFunction shiftX, DensityFunction shiftY, DensityFunction shiftZ, double xzScale, double yScale, DensityFunction.NoiseHolder noise) implements DensityFunction {
        private static final MapCodec<ShiftedNoise> DATA_CODEC = RecordCodecBuilder.mapCodec((p_208943_) -> {
            return p_208943_.group(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_x").forGetter(ShiftedNoise::shiftX), DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_y").forGetter(ShiftedNoise::shiftY), DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_z").forGetter(ShiftedNoise::shiftZ), Codec.DOUBLE.fieldOf("xz_scale").forGetter(ShiftedNoise::xzScale), Codec.DOUBLE.fieldOf("y_scale").forGetter(ShiftedNoise::yScale), DensityFunction.NoiseHolder.CODEC.fieldOf("noise").forGetter(ShiftedNoise::noise)).apply(p_208943_, ShiftedNoise::new);
        });
        public static final KeyDispatchDataCodec<ShiftedNoise> CODEC = makeCodec(DATA_CODEC);

        public double compute(DensityFunction.FunctionContext pContext) {
            double d0 = (double)pContext.blockX() * this.xzScale + this.shiftX.compute(pContext);
            double d1 = (double)pContext.blockY() * this.yScale + this.shiftY.compute(pContext);
            double d2 = (double)pContext.blockZ() * this.xzScale + this.shiftZ.compute(pContext);
            return this.noise.getValue(d0, d1, d2);
        }

        public void fillArray(double[] pArray, DensityFunction.ContextProvider pContextProvider) {
            pContextProvider.fillAllDirectly(pArray, this);
        }

        public DensityFunction mapAll(DensityFunction.Visitor pVisitor) {
            return pVisitor.apply(new ShiftedNoise(this.shiftX.mapAll(pVisitor), this.shiftY.mapAll(pVisitor), this.shiftZ.mapAll(pVisitor), this.xzScale, this.yScale, pVisitor.visitNoise(this.noise)));
        }

        public double minValue() {
            return -this.maxValue();
        }

        public double maxValue() {
            return this.noise.maxValue();
        }

        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    static <O> KeyDispatchDataCodec<O> makeCodec(MapCodec<O> pMapCodec) {
        return KeyDispatchDataCodec.of(pMapCodec);
    }
}
