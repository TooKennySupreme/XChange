/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
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
package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;

import org.joda.money.BigMoney;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampAdapters;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBooleanResponse;
import com.xeiam.xchange.bitstamp.dto.account.BitstampDepositAddress;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class BitstampAccountService extends BitstampAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   *          The {@link ExchangeSpecification}
   */
  public BitstampAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return BitstampAdapters.adaptAccountInfo(getBitstampBalance(), exchangeSpecification.getUserName());
  }

  @Override
  public String withdrawFunds(final BigMoney amount, final String address) throws IOException {

    final BitstampBooleanResponse response = withdrawBitstampFunds(amount.getAmount(), address);
    return Boolean.toString(response.getResponse());
  }

  /**
   * This returns the currently set deposit address. It will not generate a
   * new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(final String currency, final String... arguments) throws IOException {

    final BitstampDepositAddress response = getBitstampBitcoinDepositAddress();
    return response.getDepositAddress();

  }
}
