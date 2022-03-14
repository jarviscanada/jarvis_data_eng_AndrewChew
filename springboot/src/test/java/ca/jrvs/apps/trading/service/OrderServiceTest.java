package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  // Capture parameter when calling securityOrderDao.save.
  @Captor
  ArgumentCaptor<SecurityOrder> securityOrderArgumentCaptor;

  // Mock all dependencies.
  @Mock
  private AccountDao accountDao;
  @Mock
  private SecurityOrderDao securityOrderDao;
  @Mock
  private QuoteDao quoteDao;
  @Mock
  private PositionDao positionDao;

  // Inject mocked dependencies to testing class via constructor.
  @InjectMocks
  private OrderService orderService;

  @Test
  public void buyOrderSuccess() {
    Integer expectedAccountId = 1;
    Double expectedPrice = 10d;
    Integer expectedSize = 10;
    String expectedStatus = "FILLED";
    String expectedTicker = "TEST";

    MarketOrderDto buyOrder = new MarketOrderDto();
    buyOrder.setAccountId(expectedAccountId);
    buyOrder.setSize(expectedSize);
    buyOrder.setTicker(expectedTicker);

    Account account = new Account();
    account.setId(expectedAccountId);
    account.setAmount(100d);

    Quote quote = new Quote();
    quote.setTicker(expectedTicker);
    quote.setLastPrice(expectedPrice);

    when(accountDao.existsById(any())).thenReturn(true);
    when(accountDao.findById(any())).thenReturn(Optional.of(account));
    when(quoteDao.existsById(any())).thenReturn(true);
    when(quoteDao.findById(any())).thenReturn(Optional.of(quote));

    orderService.executeMarketOrder(buyOrder);

    Mockito.verify(securityOrderDao).save(securityOrderArgumentCaptor.capture());
    SecurityOrder securityOrder = securityOrderArgumentCaptor.getValue();

    assertEquals(expectedAccountId, securityOrder.getAccountId());
    assertEquals(expectedPrice, securityOrder.getPrice());
    assertEquals(expectedSize, securityOrder.getSize());
    assertEquals(expectedStatus, securityOrder.getStatus());
    assertEquals(expectedTicker, securityOrder.getTicker());
  }

  @Test
  public void sellOrderSuccess() {
    Integer expectedAccountId = 1;
    Double expectedPrice = 10d;
    Integer expectedSize = -10;
    String expectedStatus = "FILLED";
    String expectedTicker = "TEST";

    MarketOrderDto sellOrder = new MarketOrderDto();
    sellOrder.setAccountId(expectedAccountId);
    sellOrder.setSize(expectedSize);
    sellOrder.setTicker(expectedTicker);

    Account account = new Account();
    account.setId(expectedAccountId);
    account.setAmount(100d);

    Quote quote = new Quote();
    quote.setTicker(expectedTicker);
    quote.setLastPrice(expectedPrice);

    Position position = new Position();
    position.setAccountId(expectedAccountId);
    position.setPosition(-expectedSize);
    position.setTicker(expectedTicker);

    when(accountDao.existsById(any())).thenReturn(true);
    when(accountDao.findById(any())).thenReturn(Optional.of(account));
    when(quoteDao.existsById(any())).thenReturn(true);
    when(quoteDao.findById(any())).thenReturn(Optional.of(quote));
    when(positionDao.findById(any(), any())).thenReturn(Optional.of(position));

    orderService.executeMarketOrder(sellOrder);

    Mockito.verify(securityOrderDao).save(securityOrderArgumentCaptor.capture());
    SecurityOrder securityOrder = securityOrderArgumentCaptor.getValue();

    assertEquals(expectedAccountId, securityOrder.getAccountId());
    assertEquals(expectedPrice, securityOrder.getPrice());
    assertEquals(expectedSize, securityOrder.getSize());
    assertEquals(expectedStatus, securityOrder.getStatus());
    assertEquals(expectedTicker, securityOrder.getTicker());
  }

}