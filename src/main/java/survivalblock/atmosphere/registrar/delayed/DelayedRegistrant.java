/*
 * MIT License
 *
 * Copyright (c) 2025-present ekulxam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package survivalblock.atmosphere.registrar.delayed;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import survivalblock.atmosphere.registrar.Registrant;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DelayedRegistrant<T> extends Registrant<T> {
    protected final Map<String, T> delayed = new HashMap<>();

    public DelayedRegistrant(String modId, Registry<T> registry) {
        super(modId, registry);
    }

    public DelayedRegistrant(Function<String, Identifier> idFunction, Registry<T> registry) {
        super(idFunction, registry);
    }

    @Override
    public <U extends T> U register(String name, U obj) {
        this.delayed.put(name, obj);
        return obj;
    }

    protected <U extends T> void actuallyRegister(String name, U obj) {
        super.register(name, obj);
    }

    protected void registerAll() {
        this.delayed.forEach(this::actuallyRegister);
    }

    @SuppressWarnings("unused")
    public void consumeAll() {
        this.registerAll();
        this.delayed.clear();
    }
}
