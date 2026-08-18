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
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.atmosphere.registrar.shared.IRegistrant;

import java.util.function.Function;

public class Registrant<T> implements IRegistrant<T> {
    public static final Logger LOGGER = LoggerFactory.getLogger("Registrar");

    protected final Function<String, Identifier> idFunction;
    protected final Registry<T> registry;

    public Registrant(String modId, Registry<T> registry) {
        this(path -> Identifier.fromNamespaceAndPath(modId, path), registry);
    }

    public Registrant(Function<String, Identifier> idFunction, Registry<T> registry) {
        this.idFunction = idFunction;
        this.registry = registry;
    }

    @Override
    public Registry<T> getRegistry() {
        return this.registry;
    }

    @Override
    public Identifier id(String name) {
        return this.idFunction.apply(name);
    }
}
